package com.ralphmarondev.keepsafe.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    sealed interface Auth : Route {
        @Serializable
        data object Root : Auth

        @Serializable
        data object Login : Auth

        @Serializable
        data object Register : Auth
    }

    @Serializable
    sealed interface Main : Route {
        @Serializable
        data object Root : Main

        @Serializable
        data object Dashboard : Main
    }
}