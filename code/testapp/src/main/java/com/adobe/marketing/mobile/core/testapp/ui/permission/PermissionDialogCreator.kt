package com.adobe.marketing.mobile.core.testapp.ui.permission

import com.adobe.marketing.mobile.services.Log
import com.adobe.marketing.mobile.services.ServiceProvider
import com.adobe.marketing.mobile.services.ui.PermissionDialog
import com.adobe.marketing.mobile.services.ui.Presentable
import com.adobe.marketing.mobile.services.ui.PresentationError
import com.adobe.marketing.mobile.services.ui.permission.PermissionDialogEventListener
import com.adobe.marketing.mobile.services.ui.permission.PermissionDialogSettings
import com.adobe.marketing.mobile.util.DefaultPresentationUtilityProvider

object PermissionDialogCreator {

    private val permissionDialogSettings = PermissionDialogSettings()

    private val permissionDialogEventListener = object : PermissionDialogEventListener {
        override fun onPermissionGranted() {}

        override fun onPermissionDenied() {}

        override fun onPermissionDismissed() {}

        override fun onShow(presentable: Presentable<PermissionDialog>) {
            Log.debug("ServiceView", "PermissionDialogCreator", "onShow")
        }

        override fun onHide(presentable: Presentable<PermissionDialog>) {}

        override fun onDismiss(presentable: Presentable<PermissionDialog>) {}

        override fun onError(presentable: Presentable<PermissionDialog>, error: PresentationError) {}
    }

    fun create(): Presentable<PermissionDialog> = ServiceProvider.getInstance().uiService.create(
        PermissionDialog(
            permissionDialogSettings,
            permissionDialogEventListener
        ), DefaultPresentationUtilityProvider()
    )
}