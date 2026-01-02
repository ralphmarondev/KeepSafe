package com.ralphmarondev.keepsafe.core.common

actual object Constants {
    actual object Firebase {
        actual val API_KEY: String = System.getProperty("FIREBASE_API_KEY")
            ?: error("Missing FIREBASE_API_KEY")

        actual val PROJECT_ID: String = System.getProperty("FIREBASE_PROJECT_ID")
            ?: error("Missing FIREBASE_PROJECT_ID")
    }
}