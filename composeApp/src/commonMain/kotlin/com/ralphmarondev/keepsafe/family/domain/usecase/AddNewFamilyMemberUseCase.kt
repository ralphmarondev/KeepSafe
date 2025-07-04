package com.ralphmarondev.keepsafe.family.domain.usecase

import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.family.domain.model.NewFamilyMember
import com.ralphmarondev.keepsafe.family.domain.repository.FamilyRepository

class AddNewFamilyMemberUseCase(
    private val repository: FamilyRepository
) {
    suspend operator fun invoke(newFamilyMember: NewFamilyMember): Result {

        if (newFamilyMember.fullName?.isBlank() == true) {
            return Result(
                success = false,
                message = "Full name cannot be empty."
            )
        }
        if (newFamilyMember.birthday?.isBlank() == true) {
            return Result(
                success = false,
                message = "Birthday cannot be empty."
            )
        }
        if (newFamilyMember.birthplace?.isBlank() == true) {
            return Result(
                success = false,
                message = "Birthplace cannot be empty."
            )
        }
        if (newFamilyMember.role?.isBlank() == true) {
            return Result(
                success = false,
                message = "Role cannot be empty."
            )
        }
        if (newFamilyMember.email?.isBlank() == true) {
            return Result(
                success = false,
                message = "Email cannot be empty."
            )
        }
        if (newFamilyMember.password?.isBlank() == true) {
            return Result(
                success = false,
                message = "Password cannot be empty."
            )
        }

        return try {
            repository.addNewFamilyMember(newFamilyMember)
            Result(
                success = true,
                message = "${newFamilyMember.fullName} registered successfully!"
            )
        } catch (e: Exception) {
            Result(
                success = false,
                message = "Registration failed. Error: ${e.message}"
            )
        }
    }
}