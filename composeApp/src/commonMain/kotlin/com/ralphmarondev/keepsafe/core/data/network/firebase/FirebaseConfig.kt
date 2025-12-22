package com.ralphmarondev.keepsafe.core.data.network.firebase

import com.ralphmarondev.keepsafe.core.common.Constants

object FirebaseConfig {
    val API_KEY: String = Constants.Firebase.API_KEY
    val PROJECT_ID: String = Constants.Firebase.PROJECT_ID
    const val AUTH_BASE_URL = "https://identitytoolkit.googleapis.com/v1"
    val FIRESTORE_BASE_URL =
        "https://firestore.googleapis.com/v1/projects/$PROJECT_ID/databases/(default)/documents"
}
