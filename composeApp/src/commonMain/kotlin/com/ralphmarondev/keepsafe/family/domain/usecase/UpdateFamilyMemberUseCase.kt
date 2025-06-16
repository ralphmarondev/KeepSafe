package com.ralphmarondev.keepsafe.family.domain.usecase

import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository

class UpdateFamilyMemberUseCase(
    private val repository: FamilyRepository
) {
    suspend operator fun invoke(
        familyMember: FamilyMember
    ): Boolean {
        if (familyMember.uid.isNullOrBlank()) {
            return false
        }
        return repository.updateFamilyMember(familyMember = familyMember)
    }
}