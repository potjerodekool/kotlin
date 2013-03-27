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

package org.jetbrains.jet.lang.resolve.java;

import com.intellij.psi.PsiClass;
import org.jetbrains.jet.lang.descriptors.PackageFragmentDescriptor;
import org.jetbrains.jet.lang.resolve.BindingContext;
import org.jetbrains.jet.util.slicedmap.Slices;
import org.jetbrains.jet.util.slicedmap.WritableSlice;

/**
 * @see BindingContext
 */
public class JavaBindingContext {

    // Static members of this class constitute the package
    public static final WritableSlice<PackageFragmentDescriptor, PsiClass> JAVA_STATIC_CLASS_FOR_PACKAGE = Slices.createSimpleSlice();

    private JavaBindingContext() {
    }
}
