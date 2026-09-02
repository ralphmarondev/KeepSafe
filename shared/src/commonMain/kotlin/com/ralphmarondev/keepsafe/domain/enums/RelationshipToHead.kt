package com.ralphmarondev.keepsafe.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class RelationshipToHead {
    SELF,
    SPOUSE,
    FIRST_CHILD,
    SECOND_CHILD,
    THIRD_CHILD
}