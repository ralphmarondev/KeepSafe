package com.ralphmarondev.keepsafe.feature.family.presentation.new_member

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.domain.model.Account
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import com.ralphmarondev.keepsafe.domain.repository.MemberRepository
import com.ralphmarondev.keepsafe.feature.family.domain.enums.MemberRegistrationPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewMemberViewModel(
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NewMemberState())
    val state = _state.asStateFlow()

    fun onAction(action: NewMemberAction) {
        when (action) {
            is NewMemberAction.FirstNameChange -> firstNameChange(action.firstName)
            is NewMemberAction.MiddleNameChange -> middleNameChange(action.middleName)
            is NewMemberAction.LastNameChange -> lastNameChange(action.lastName)
            is NewMemberAction.MaidenNameChange -> maidenNameChange(action.maidenName)
            is NewMemberAction.ContactNumberChange -> contactNumberChange(action.contactNumber)
            is NewMemberAction.BirthdayChange -> birthdayChange(action.birthday)
            is NewMemberAction.UsernameChange -> usernameChange(action.username)
            is NewMemberAction.PasswordChange -> passwordChange(action.password)
            is NewMemberAction.ConfirmPasswordChange -> confirmPasswordChange(action.confirmPassword)
            is NewMemberAction.ChangePage -> changePage(action.page)
            NewMemberAction.Save -> save()
        }
    }


    private fun firstNameChange(firstName: String) {
        _state.update { it.copy(firstName = firstName) }
    }

    private fun middleNameChange(middleName: String) {
        _state.update { it.copy(middleName = middleName) }
    }

    private fun lastNameChange(lastName: String) {
        _state.update { it.copy(lastName = lastName) }
    }

    private fun maidenNameChange(maidenName: String) {
        _state.update { it.copy(maidenName = maidenName) }
    }

    private fun contactNumberChange(contactNumber: String) {
        _state.update { it.copy(contactNumber = contactNumber) }
    }

    private fun birthdayChange(birthday: String) {
        _state.update { it.copy(birthday = birthday) }
    }

    private fun usernameChange(username: String) {
        _state.update { it.copy(username = username.lowercase()) }
    }

    private fun passwordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    private fun confirmPasswordChange(confirmPassword: String) {
        _state.update { it.copy(confirmPassword = confirmPassword) }
    }

    private fun changePage(page: MemberRegistrationPage) {
        when (page) {
            MemberRegistrationPage.BasicInformation -> basicInformation()
            MemberRegistrationPage.MoreInformation -> moreInformation()
            MemberRegistrationPage.AccountInformation -> accountInformation()
        }
    }

    private fun basicInformation() {
        _state.update { it.copy(page = MemberRegistrationPage.BasicInformation) }
    }

    private fun moreInformation() {
        val firstName = _state.value.firstName.trim()
        val lastName = _state.value.lastName.trim()
        var isValid = true

        _state.update {
            it.copy(
                firstNameError = false,
                firstNameErrorMessage = null,
                lastNameError = false,
                lastNameErrorMessage = null
            )
        }

        if (firstName.isBlank()) {
            isValid = false
            _state.update {
                it.copy(
                    firstNameError = true,
                    firstNameErrorMessage = "First name is required."
                )
            }
        }
        if (lastName.isBlank()) {
            isValid = false
            _state.update {
                it.copy(
                    lastNameError = true,
                    lastNameErrorMessage = "Last name is required."
                )
            }
        }

        if (isValid) {
            _state.update { it.copy(page = MemberRegistrationPage.MoreInformation) }
            return
        }
    }

    private fun accountInformation() {
        val contactNumber = _state.value.contactNumber.trim()
        val birthday = _state.value.birthday.trim()
        var isValid = true

        _state.update {
            it.copy(
                contactNumberError = false,
                contactNumberErrorMessage = null,
                birthdayError = false,
                birthdayErrorMessage = null
            )
        }

        if (contactNumber.isBlank()) {
            isValid = false
            _state.update {
                it.copy(
                    contactNumberError = true,
                    contactNumberErrorMessage = "Contact number is required."
                )
            }
        }
        if (birthday.isBlank()) {
            isValid = false
            _state.update {
                it.copy(
                    birthdayError = true,
                    birthdayErrorMessage = "Birthday is required."
                )
            }
        }

        if (isValid) {
            _state.update { it.copy(page = MemberRegistrationPage.AccountInformation) }
            return
        }
    }

    private fun save() {
        viewModelScope.launch {
            try {
                val username = _state.value.username.trim().lowercase()
                val password = _state.value.password.trim()
                val confirmPassword = _state.value.confirmPassword.trim()
                var isValid = true

                if (state.value.isSaving)
                    return@launch

                _state.update {
                    it.copy(
                        usernameError = false,
                        usernameErrorMessage = null,
                        passwordError = false,
                        passwordErrorMessage = null,
                        confirmPasswordError = false,
                        confirmPasswordErrorMessage = null,
                        isSaving = true,
                        errorMessage = null,
                        showErrorMessage = false,
                        isSaved = false
                    )
                }

                if (username.isBlank()) {
                    isValid = false
                    _state.update {
                        it.copy(
                            usernameError = true,
                            usernameErrorMessage = "Username cannot be empty."
                        )
                    }
                } else if (!username.matches(Regex("^[a-z0-9]+$"))) {
                    isValid = false
                    _state.update {
                        it.copy(
                            usernameError = true,
                            usernameErrorMessage = "Username can only contain letters and numbers."
                        )
                    }
                }

                if (password.isBlank()) {
                    isValid = false
                    _state.update {
                        it.copy(
                            passwordError = true,
                            passwordErrorMessage = "Password cannot be empty."
                        )
                    }
                }

                if (password != confirmPassword) {
                    isValid = false
                    _state.update {
                        it.copy(
                            confirmPasswordError = true,
                            confirmPasswordErrorMessage = "Password not matched."
                        )
                    }
                }

                if (isValid) {
                    val member = Member(
                        firstName = _state.value.firstName.trim(),
                        lastName = _state.value.lastName.trim(),
                        middleName = _state.value.middleName.trim(),
                        maidenName = _state.value.maidenName.trim()
                    )
                    val account = Account(
                        username = _state.value.username.trim(),
                        password = _state.value.password.trim()
                    )
                    val result = memberRepository.create(
                        member = member,
                        account = account
                    )

                    when (result) {
                        is Result.Success -> {
                            _state.update { it.copy(isSaved = true) }
                        }

                        is Result.Error -> {
                            _state.update {
                                it.copy(
                                    usernameError = result.message?.contains(
                                        "Username is already taken.",
                                        ignoreCase = true
                                    ) == true,
                                    usernameErrorMessage = result.message
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = "Registration failed!",
                        showErrorMessage = true
                    )
                }
                Log.e("Registration", "Failed. Error: ${e.message}")
                e.printStackTrace()
            } finally {
                _state.update { it.copy(isSaving = false) }
            }
        }
    }
}