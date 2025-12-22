package com.ralphmarondev.keepsafe.core.common

import java.io.File

actual object Constants {
    actual object Firebase {
        private val properties by lazy {
            java.util.Properties().apply {
                val projectDir = File("").absoluteFile
                val configFile = File(projectDir, "config.properties")
                if (configFile.exists()) {
                    load(configFile.inputStream())
                }
            }
        }

        actual val API_KEY: String
            get() = properties.getProperty("FIREBASE_API_KEY", "\"MISSING_API_KEY\"")
        actual val PROJECT_ID: String
            get() = properties.getProperty("FIREBASE_PROJECT_ID", "\"MISSING_PROJECT_ID\"")
    }
}