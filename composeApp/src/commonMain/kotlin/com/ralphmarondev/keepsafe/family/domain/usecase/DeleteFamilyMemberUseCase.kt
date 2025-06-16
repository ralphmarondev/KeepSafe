package com.ralphmarondev.keepsafe.family.domain.usecase

import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository

class DeleteFamilyMemberUseCase(
    private val repository: FamilyRepository
) {
    suspend operator fun invoke(uid: String): Boolean {
        return repository.deleteFamilyMember(uid)
    }
}