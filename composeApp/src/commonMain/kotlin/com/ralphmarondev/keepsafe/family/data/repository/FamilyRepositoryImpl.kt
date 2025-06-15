package com.ralphmarondev.keepsafe.family.data.repository

import com.ralphmarondev.keepsafe.family.data.network.FamilyApiService
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.model.NewFamilyMember
import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository

class FamilyRepositoryImpl(
    private val familyApiService: FamilyApiService
) : FamilyRepository {

    override suspend fun addNewFamilyMember(newFamilyMember: NewFamilyMember) {
        familyApiService.registerNewFamilyMember(
            email = newFamilyMember.email,
            fullName = newFamilyMember.fullName,
            familyId = newFamilyMember.familyId,
            password = newFamilyMember.password,
            role = newFamilyMember.role,
            birthday = newFamilyMember.birthday,
            birthplace = newFamilyMember.birthplace
        )
    }

    override suspend fun getFamilyMembers(idToken: String, familyId: String): List<FamilyMember> {
        return familyApiService.getFamilyMembers(idToken = idToken, familyId = familyId)
    }
}