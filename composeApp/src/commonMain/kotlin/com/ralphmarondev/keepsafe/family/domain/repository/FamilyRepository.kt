package com.ralphmarondev.keepsafe.family.domain.repository

import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.model.NewFamilyMember

interface FamilyRepository {

    suspend fun addNewFamilyMember(newFamilyMember: NewFamilyMember)

    suspend fun getFamilyMembers(idToken: String, familyId: String): List<FamilyMember>
}