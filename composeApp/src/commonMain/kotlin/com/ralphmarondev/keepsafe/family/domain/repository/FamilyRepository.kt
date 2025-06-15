package com.ralphmarondev.keepsafe.family.domain.repository

import com.ralphmarondev.keepsafe.family.domain.model.NewFamilyMember

interface FamilyRepository {

    suspend fun addNewFamilyMember(newFamilyMember: NewFamilyMember)
}