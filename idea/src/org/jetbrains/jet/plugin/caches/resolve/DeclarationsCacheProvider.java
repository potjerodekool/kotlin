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

package org.jetbrains.jet.plugin.caches.resolve;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.psi.util.CachedValue;
import org.jetbrains.jet.plugin.project.TargetPlatform;

abstract class DeclarationsCacheProvider {
    protected final TargetPlatform platform;
    protected final Project project;
    protected final Key<CachedValue<KotlinDeclarationsCache>> cachedKey;

    DeclarationsCacheProvider(Project project, TargetPlatform platform, String keyName) {
        this.platform = platform;
        this.cachedKey = Key.create(keyName);
        this.project = project;
    }

    public abstract KotlinDeclarationsCache getDeclarations(boolean allowIncomplete);
}
