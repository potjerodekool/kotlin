/*
 * Copyright 2010-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.codegen;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.asm4.MethodVisitor;
import org.jetbrains.asm4.Type;
import org.jetbrains.asm4.commons.InstructionAdapter;
import org.jetbrains.jet.codegen.context.CodegenContext;
import org.jetbrains.jet.codegen.state.GenerationState;
import org.jetbrains.jet.codegen.state.GenerationStateAware;
import org.jetbrains.jet.codegen.state.JetTypeMapperMode;
import org.jetbrains.jet.lang.descriptors.ClassDescriptor;
import org.jetbrains.jet.lang.descriptors.FunctionDescriptor;
import org.jetbrains.jet.lang.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.jet.lang.resolve.java.JvmClassName;
import org.jetbrains.jet.lang.resolve.java.sam.SingleAbstractMethodUtils;
import org.jetbrains.jet.lang.resolve.name.Name;
import org.jetbrains.jet.lang.types.JetType;

import static org.jetbrains.asm4.Opcodes.*;
import static org.jetbrains.jet.codegen.AsmUtil.NO_FLAG_PACKAGE_PRIVATE;
import static org.jetbrains.jet.codegen.AsmUtil.genStubCode;
import static org.jetbrains.jet.lang.resolve.java.AsmTypeConstants.OBJECT_TYPE;

public class SamWrapperCodegen extends GenerationStateAware {
    private static final String FUNCTION_FIELD_NAME = "function";

    public SamWrapperCodegen(@NotNull GenerationState state) {
        super(state);
    }

    public void genWrapper(
            @NotNull PsiElement origin,
            @NotNull JvmClassName name,    // E. g. package.bar.Foo$bar$1
            @NotNull JetType functionType, // E. g. (Int, Int) -> Int
            @NotNull JetType samType       // Comparator<Int>
    ) {
        // e.g. compare(Int, Int)
        SimpleFunctionDescriptor interfaceFunction = SingleAbstractMethodUtils.getAbstractMethodOfSamType(samType);

        ClassDescriptor resultClass = (ClassDescriptor) samType.getConstructor().getDeclarationDescriptor();
        assert resultClass != null : "Null classifier for " + samType;

        ClassBuilder cv = state.getFactory().newVisitor(name.getInternalName(), origin.getContainingFile());
        cv.defineClass(origin,
                       V1_6,
                       ACC_FINAL,
                       name.getInternalName(),
                       null,
                       OBJECT_TYPE.getInternalName(),
                       new String[] {JvmClassName.byClassDescriptor(resultClass).getInternalName()}
        );
        cv.visitSource(origin.getContainingFile().getName(), null);

        // e.g. ASM type for Function2
        Type functionAsmType = state.getTypeMapper().mapType(functionType, JetTypeMapperMode.VALUE);

        cv.newField(null,
                    ACC_SYNTHETIC | ACC_PRIVATE | ACC_FINAL,
                    FUNCTION_FIELD_NAME,
                    functionAsmType.getDescriptor(),
                    null,
                    null);

        generateConstructor(name.getAsmType(), functionAsmType, cv);
        generateMethod(name.getAsmType(), functionAsmType, cv, interfaceFunction, functionType);

        cv.done();
    }

    private void generateConstructor(Type ownerType, Type functionType, ClassBuilder cv) {
        MethodVisitor mv = cv.newMethod(null, NO_FLAG_PACKAGE_PRIVATE, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, functionType), null, null);

        if (state.getClassBuilderMode() == ClassBuilderMode.STUBS) {
            genStubCode(mv);
        }
        else if (state.getClassBuilderMode() == ClassBuilderMode.FULL) {
            mv.visitCode();
            InstructionAdapter iv = new InstructionAdapter(mv);

            // super constructor
            iv.load(0, OBJECT_TYPE);
            iv.invokespecial(OBJECT_TYPE.getInternalName(), "<init>", "()V");

            // save parameter to field
            iv.load(0, OBJECT_TYPE);
            iv.load(1, functionType);
            iv.putfield(ownerType.getInternalName(), FUNCTION_FIELD_NAME, functionType.getDescriptor());

            iv.visitInsn(RETURN);
            FunctionCodegen.endVisit(iv, "constructor of SAM wrapper", null);
        }
    }

    private void generateMethod(
            Type ownerType,
            Type functionType,
            ClassBuilder cv,
            SimpleFunctionDescriptor interfaceFunction,
            JetType functionJetType
    ) {

        // using static context to avoid creating ClassDescriptor and everything else
        FunctionCodegen codegen = new FunctionCodegen(CodegenContext.STATIC, cv, state);

        FunctionDescriptor invokeFunction = functionJetType.getMemberScope()
                .getFunctions(Name.identifier("invoke")).iterator().next().getOriginal();
        StackValue functionField = StackValue.field(functionType, JvmClassName.byType(ownerType), FUNCTION_FIELD_NAME, false);
        codegen.genDelegate(interfaceFunction, invokeFunction, functionField);
    }
}
