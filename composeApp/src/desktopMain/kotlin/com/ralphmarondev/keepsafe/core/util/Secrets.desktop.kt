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

    actual val LOGIN_URL: String = props.getProperty("LOGIN_URL") ?: error("Missing LOGIN_URL")
    actual val REGISTER_URL: String = props.getProperty("REGISTER_URL") ?: error("Missing REGISTER_URL")
    actual val DATABASE_URL: String = props.getProperty("DATABASE_URL") ?: error("Missing DATABASE_URL")
    actual val ENCRYPTION_KEY: String = props.getProperty("ENCRYPTION_KEY") ?: error("Missing ENCRYPTION_KEY")
}