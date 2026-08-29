package com.ralphmarondev.keepsafe.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object Auth : Route

    @Serializable
    data object Login : Route
}