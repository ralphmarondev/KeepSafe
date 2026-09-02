package com.ralphmarondev.keepsafe.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    // Auth Routes
    @Serializable
    data object Auth : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object Register : Route


    // Main Routes
    @Serializable
    data object Main : Route

    @Serializable
    data object MemberList : Route

    @Serializable
    data class MemberDetail(val uid: String) : Route

    // Account Routes
    @Serializable
    data object Account : Route

    @Serializable
    data object Overview : Route
}