package com.ralphmarondev.keepsafe.core.util

expect object Secrets{
    val apiKey: String
    val authUrl: String
    val familyListUrl: String
}