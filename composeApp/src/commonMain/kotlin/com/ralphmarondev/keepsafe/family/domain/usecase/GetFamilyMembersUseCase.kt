package com.ralphmarondev.keepsafe.family.domain.usecase

import com.ralphmarondev.keepsafe.core.domain.model.ResultWithData
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository

class GetFamilyMembersUseCase(
    private val repository: FamilyRepository
) {
    suspend operator fun invoke(
        idToken: String,
        familyId: String
    ): ResultWithData<out List<FamilyMember>> {

        if (idToken.isBlank()) {
            return ResultWithData.error("idToken cannot be blank.")
        }
        if (familyId.isBlank()) {
            return ResultWithData.error("familyId cannot be blank.")
        }

        return try {
            val members = repository.getFamilyMembers(idToken, familyId)
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