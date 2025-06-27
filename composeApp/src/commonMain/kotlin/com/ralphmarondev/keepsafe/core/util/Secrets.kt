package com.ralphmarondev.keepsafe.core.util

expect object Secrets{
    val REGISTER_URL: String
    val LOGIN_URL: String
    val DATABASE_URL: String
    val ENCRYPTION_KEY: String
}