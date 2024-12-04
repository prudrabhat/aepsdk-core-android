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

package com.adobe.marketing.mobile.services.ui.permisson.views

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.adobe.marketing.mobile.services.ui.permission.PermissionDialogEventListener

@Composable
internal fun PermissionProvider(
    permissionDialogEventListener: PermissionDialogEventListener?,
    completion: () -> Unit
) {
    val context = LocalContext.current
    val permissionState =
        remember {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) ==
                        PackageManager.PERMISSION_GRANTED
                )
            } else {
                Log.d("PermissionProvider", "Permission not supported on this device")
                mutableStateOf(true)
            }
        }.also {
            Log.d("PermissionProvider", "Permission state: ${it.value}")
        }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Update the state of the permission
        permissionState.value = isGranted

        if (isGranted) {
            permissionDialogEventListener?.onPermissionGranted()
        }

        completion()
    }

    LaunchedEffect(Unit) {
        Log.d("PermissionProvider", "Requesting permission")
        launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
    }
}
