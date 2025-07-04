package com.ralphmarondev.keepsafe.family.domain.repository

import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.model.NewFamilyMember

interface FamilyRepository {

    suspend fun addNewFamilyMember(newFamilyMember: NewFamilyMember)

    suspend fun getFamilyMembers(familyId: String): List<FamilyMember>

    suspend fun getMemberDetails(uid: String): FamilyMember?

    suspend fun updateFamilyMember(familyMember: FamilyMember): Boolean

    suspend fun deleteFamilyMember(uid: String): Boolean
}