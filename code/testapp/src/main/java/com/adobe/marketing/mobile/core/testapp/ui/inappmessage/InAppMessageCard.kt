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
package com.adobe.marketing.mobile.core.testapp.ui.inappmessage

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import com.adobe.marketing.mobile.services.ui.InAppMessage
import com.adobe.marketing.mobile.services.ui.Presentable

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InAppMessageCard(iamPresentable: Presentable<InAppMessage>) {

    Card(modifier = Modifier.padding(16.dp).semantics { testTagsAsResourceId = true }, elevation = 8.dp) {
        Column(
            modifier = Modifier.fillMaxWidth()
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "InAppMessage", modifier = Modifier.padding(16.dp))

            Row(
                modifier = Modifier.width(300.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { iamPresentable.show() }, modifier = Modifier.testTag("show_in_app_message")) {
                    Text(text = "Show")
                }

                Button(onClick = { iamPresentable.hide() }) {
                    Text(text = "Hide")
                }

                Button(onClick = { iamPresentable.dismiss() }, modifier = Modifier.testTag("dismiss_in_app_message")) {
                    Text(text = "Dismiss")
                }
            }
        }

    }
}