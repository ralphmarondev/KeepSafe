package com.ralphmarondev.keepsafe.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object Login : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data object NewFamilyMember : Routes

    @Serializable
    data class FamilyMemberDetail(val id: String) : Routes

    @Serializable
    data class UpdateFamilyMember(val uid: String) : Routes
}
