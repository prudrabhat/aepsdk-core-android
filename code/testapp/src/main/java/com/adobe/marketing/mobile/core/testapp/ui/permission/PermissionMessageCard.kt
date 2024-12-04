package com.adobe.marketing.mobile.core.testapp.ui.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adobe.marketing.mobile.services.ui.InAppMessage
import com.adobe.marketing.mobile.services.ui.PermissionDialog
import com.adobe.marketing.mobile.services.ui.Presentable
import com.adobe.marketing.mobile.services.ui.Presentation

@Composable
fun PermissionMessageCard(permissionPresentable: Presentable<PermissionDialog>) {

    Card(modifier = Modifier.padding(16.dp), elevation = 8.dp) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Permission Dialog", modifier = Modifier.padding(16.dp))

            Row(
                modifier = Modifier.width(300.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { permissionPresentable.show() }) {
                    Text(text = "Show")
                }

                Button(onClick = { permissionPresentable.hide() }) {
                    Text(text = "Hide")
                }

                Button(onClick = { permissionPresentable.dismiss() }) {
                    Text(text = "Dismiss")
                }
            }
        }

    }
}