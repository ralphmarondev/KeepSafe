package com.ralphmarondev.keepsafe.family.domain.usecase

import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.domain.model.ResultWithData
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository
import kotlinx.coroutines.flow.first

class GetFamilyMembersUseCase(
    private val repository: FamilyRepository,
    private val preferences: AppPreferences
) {
    suspend operator fun invoke(): ResultWithData<out List<FamilyMember>> {
        val familyId = preferences.familyId().first()

        if (familyId.isBlank()) {
            return ResultWithData.error("familyId cannot be blank.")
        }

        return try {
            val members = repository.getFamilyMembers(familyId)
            if (members.isNotEmpty()) {
                ResultWithData.success("Getting family members successful.", members)
            } else {
                ResultWithData.success("No family members found.", emptyList())
            }
        } catch (e: Exception) {
            ResultWithData.error("Failed to get family members: ${e.message}")
        }
    }
}