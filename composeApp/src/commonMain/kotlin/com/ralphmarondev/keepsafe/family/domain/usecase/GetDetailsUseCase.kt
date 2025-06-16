package com.ralphmarondev.keepsafe.family.domain.usecase

import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository

class GetDetailsUseCase(
    private val repository: FamilyRepository
) {
    suspend operator fun invoke(uid: String): FamilyMember? {
        val result = repository.getMemberDetails(uid)

        return result
    }
}