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
    data object Dashboard : Route
}