package com.ralphmarondev.keepsafe.family.data.repository

import com.ralphmarondev.keepsafe.family.data.network.FamilyApiService
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.model.NewFamilyMember
import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository

class FamilyRepositoryImpl(
    private val familyApiService: FamilyApiService
) : FamilyRepository {

    override suspend fun addNewFamilyMember(newFamilyMember: NewFamilyMember) {

    }

    override suspend fun getFamilyMembers(idToken: String, familyId: String): List<FamilyMember> {
        return emptyList()
    }

    override suspend fun getMemberDetails(uid: String): FamilyMember? {
        return null
    }

    override suspend fun updateFamilyMember(familyMember: FamilyMember) {

    }

    override suspend fun deleteFamilyMember(uid: String) {

    }
}