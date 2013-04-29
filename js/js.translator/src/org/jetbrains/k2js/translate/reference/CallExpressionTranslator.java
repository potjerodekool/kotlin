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

package org.jetbrains.k2js.translate.reference;

import com.google.dart.compiler.backend.js.ast.*;
import com.intellij.openapi.util.Pair;
import com.intellij.util.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jet.lang.descriptors.CallableDescriptor;
import org.jetbrains.jet.lang.descriptors.ValueParameterDescriptor;
import org.jetbrains.jet.lang.psi.JetCallExpression;
import org.jetbrains.jet.lang.psi.JetExpression;
import org.jetbrains.jet.lang.psi.JetSimpleNameExpression;
import org.jetbrains.jet.lang.resolve.calls.model.ResolvedCall;
import org.jetbrains.jet.lang.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.jet.lang.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.jet.lang.resolve.calls.util.ExpressionAsFunctionDescriptor;
import org.jetbrains.k2js.translate.context.TranslationContext;
import org.jetbrains.k2js.translate.general.Translation;
import org.jetbrains.k2js.translate.utils.AnnotationsUtils;
import org.jetbrains.k2js.translate.utils.PsiUtils;

import java.util.ArrayList;
import java.util.List;

import static org.jetbrains.k2js.translate.utils.PsiUtils.getCallee;

public final class CallExpressionTranslator extends AbstractCallExpressionTranslator {

    @NotNull
    public static JsExpression translate(@NotNull JetCallExpression expression,
            @Nullable JsExpression receiver,
            @NotNull CallType callType,
            @NotNull TranslationContext context) {
        if (InlinedCallExpressionTranslator.shouldBeInlined(expression, context)) {
            return InlinedCallExpressionTranslator.translate(expression, receiver, callType, context);
        }
        return (new CallExpressionTranslator(expression, receiver, callType, context)).translate();
    }

    private final boolean isNativeFunctionCall;

    private CallExpressionTranslator(@NotNull JetCallExpression expression,
            @Nullable JsExpression receiver,
            @NotNull CallType callType, @NotNull TranslationContext context) {
        super(expression, receiver, callType, context);
        this.isNativeFunctionCall = AnnotationsUtils.isNativeObject(resolvedCall.getCandidateDescriptor());
    }

    @NotNull
    private JsExpression translate() {
        List<JsExpression> args = translateArguments();

        if (isNativeFunctionCall && hasSpreadOperator) {
            String functionName = resolvedCall.getCandidateDescriptor().getOriginal().getName().getIdentifier();

            JsNameRef function;
            if (receiver != null) {
                Pair<JsVars.JsVar, JsNameRef> evaluatedReceiver = context().dynamicContext().createTemporary(receiver);
                context().addStatementToCurrentBlock(new JsVars(evaluatedReceiver.first));

                function = new JsNameRef(functionName, evaluatedReceiver.second);
                args.add(0, evaluatedReceiver.second);
            }
            else {
                function = new JsNameRef(functionName);
                args.add(0, JsLiteral.NULL);
            }

            return new JsInvocation(new JsNameRef("apply", function), args);
        }

        return CallBuilder.build(context())
                .receiver(getReceiver())
                .callee(getCalleeExpression())
                .args(args)
                .resolvedCall(getResolvedCall())
                .type(callType)
                .translate();
    }

    @NotNull
    private ResolvedCall<?> getResolvedCall() {
        if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
            return ((VariableAsFunctionResolvedCall) resolvedCall).getFunctionCall();
        }
        return resolvedCall;
    }

    @Nullable
    private JsExpression getReceiver() {
        if (receiver != null) {
            return receiver;
        }
        return null;
    }

    @Nullable
    private JsExpression getCalleeExpression() {
        CallableDescriptor candidateDescriptor = resolvedCall.getCandidateDescriptor();
        if (candidateDescriptor instanceof ExpressionAsFunctionDescriptor) {
            return translateExpressionAsFunction();
        }
        if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
            return translateVariableForVariableAsFunctionResolvedCall();
        }
        return null;
    }

    @NotNull
    //TODO: looks hacky and should be modified soon
    private JsExpression translateVariableForVariableAsFunctionResolvedCall() {
        JetExpression callee = PsiUtils.getCallee(expression);
        if (callee instanceof JetSimpleNameExpression) {
            return ReferenceTranslator.getAccessTranslator((JetSimpleNameExpression) callee, receiver, context()).translateAsGet();
        }
        assert receiver != null;
        return Translation.translateAsExpression(callee, context());
    }

    @NotNull
    private JsExpression translateExpressionAsFunction() {
        return Translation.translateAsExpression(getCallee(expression), context());
    }

    @NotNull
    private List<JsExpression> translateArguments() {
        List<JsExpression> result = new ArrayList<JsExpression>();
        List<JsExpression> argsBeforeVararg = null;
        for (ValueParameterDescriptor parameterDescriptor : resolvedCall.getResultingDescriptor().getValueParameters()) {
            ResolvedValueArgument actualArgument = resolvedCall.getValueArgumentsByIndex().get(parameterDescriptor.getIndex());

            List<JsExpression> translatedArgument = translateSingleArgument(actualArgument, parameterDescriptor);

            // if this is native call and translatedArgument has spread operator
            if (isNativeFunctionCall && hasSpreadOperator) {
                assert argsBeforeVararg == null;
                argsBeforeVararg = result;
                result = new ArrayList<JsExpression>();
            }

            result.addAll(translatedArgument);
        }

        if (argsBeforeVararg != null && !argsBeforeVararg.isEmpty()) {
            JsInvocation concatArguments = new JsInvocation(new JsNameRef("concat", new JsArrayLiteral(argsBeforeVararg)), result);
            result = new SmartList<JsExpression>(concatArguments);
        }

        return result;
    }

    @Override
    public boolean shouldWrapVarargInArray() {
        return !isNativeFunctionCall;
    }
}
