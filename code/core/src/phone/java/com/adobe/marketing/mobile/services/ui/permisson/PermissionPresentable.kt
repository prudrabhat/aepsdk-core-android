/*
  Copyright 2024 Adobe. All rights reserved.
  This file is licensed to you under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software distributed under
  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
  OF ANY KIND, either express or implied. See the License for the specific language
  governing permissions and limitations under the License.
*/

package com.adobe.marketing.mobile.services.ui.permisson

import android.content.Context
import androidx.compose.ui.platform.ComposeView
import com.adobe.marketing.mobile.services.ui.PermissionDialog
import com.adobe.marketing.mobile.services.ui.Presentation
import com.adobe.marketing.mobile.services.ui.PresentationDelegate
import com.adobe.marketing.mobile.services.ui.PresentationUtilityProvider
import com.adobe.marketing.mobile.services.ui.common.AEPPresentable
import com.adobe.marketing.mobile.services.ui.common.AppLifecycleProvider
import com.adobe.marketing.mobile.services.ui.permisson.views.PermissionProvider
import kotlinx.coroutines.CoroutineScope

internal class PermissionPresentable(
    val permissionDialog: PermissionDialog,
    presentationDelegate: PresentationDelegate?,
    presentationUtilityProvider: PresentationUtilityProvider,
    appLifecycleProvider: AppLifecycleProvider,
    mainScope: CoroutineScope
) : AEPPresentable<PermissionDialog>(
    permissionDialog,
    presentationUtilityProvider,
    presentationDelegate,
    appLifecycleProvider,
    mainScope
) {
    override fun getContent(activityContext: Context): ComposeView {
        return ComposeView(activityContext).apply {
            setContent {
                PermissionProvider(permissionDialog.eventListener) {
                    dismiss()
                }
            }
        }
    }

    override fun gateDisplay(): Boolean {
        return false
    }

    override fun hasConflicts(visiblePresentations: List<Presentation<*>>): Boolean {
        return false
    }

    override fun getPresentation(): PermissionDialog {
        return permissionDialog
    }
}
