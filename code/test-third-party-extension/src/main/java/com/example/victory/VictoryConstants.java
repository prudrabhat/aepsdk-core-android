/*
  Copyright 2022 Adobe. All rights reserved.
  This file is licensed to you under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software distributed under
  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
  OF ANY KIND, either express or implied. See the License for the specific language
  governing permissions and limitations under the License.
 */

package com.example.victory;

class VictoryConstants {

	static final String EVENT_TYPE_CONFIGURATION = "com.adobe.eventType.configuration";
	static final String EVENT_TYPE_HUB = "com.adobe.eventType.hub";
	static final String EVENT_TYPE_VICTORY = "com.example.victory.custom";
	static final String EVENT_SOURCE_RESPONSE_CONTENT = "com.adobe.eventSource.responsecontent";
	static final String EVENT_SOURCE_SHARED_STATE = "com.adobe.eventSource.sharedstate";
	static final String EVENT_SOURCE_VICTORY_PAIRED_REQUEST = "com.example.victory.pairedrequest";
	static final String EVENT_SOURCE_VICTORY_PAIRED_RESPONSE = "com.example.victory.pairedresponse";
	static final String EVENT_SOURCE_VICTORY_REQUEST = "com.example.victory.request";
	static final String STATE_OWNER = "stateowner";
	static final String CONFIGURATION_SHARED_STATE = "com.adobe.module.configuration";
	static final String NO_EVENTS_PROCESSED = "eventsprocessed";
	static final String CONTEXT_DATA = "victorycontextdata";
	static final String UNREGISTER_EXTENSION = "unregisterextension";
	static final String GOTO_ACTIVITY_NAME = "victorygotoactivity";
	static final String PRINT_LATEST_CONFIG = "printlatestconfig";

	private VictoryConstants() {}
}
