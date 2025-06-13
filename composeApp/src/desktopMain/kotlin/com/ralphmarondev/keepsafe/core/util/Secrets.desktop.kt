package com.ralphmarondev.keepsafe.core.util

import java.io.File
import java.util.Properties

actual object Secrets {
    private val props = Properties().apply {
        val root = File(System.getProperty("user.dir")).parentFile
        val local = File(root, "local.properties")
        println("Reading secrets from ${local.absolutePath}")
        load(local.inputStream().reader())
    }

    actual val apiKey: String = props.getProperty("API_KEY") ?: error("Missing API_KEY")
    actual val authUrl: String = props.getProperty("AUTHENTICATION_URL") ?: error("Missing AUTHENTICATION_URL")
    actual val familyListUrl: String = props.getProperty("FAMILY_LIST_URL") ?: error("Missing FAMILY_LIST_URL")
}