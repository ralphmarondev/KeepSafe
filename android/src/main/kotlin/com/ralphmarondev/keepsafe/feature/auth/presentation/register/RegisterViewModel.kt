package com.ralphmarondev.keepsafe.feature.auth.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.domain.model.Account
import com.ralphmarondev.keepsafe.domain.model.Family
import com.ralphmarondev.keepsafe.domain.model.Member
import com.ralphmarondev.keepsafe.domain.model.Result
import com.ralphmarondev.keepsafe.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    init {
        generateFamilyCode()
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.FamilyNameChange -> familyNameChange(action.name)
            is RegisterAction.FirstNameChange -> firstNameChange(action.firstName)
            is RegisterAction.MiddleNameChange -> middleNameChange(action.middleName)
            is RegisterAction.LastNameChange -> lastNameChange(action.lastName)
            is RegisterAction.MaidenNameChange -> maidenNameChange(action.maidenName)
            is RegisterAction.UsernameChange -> usernameChange(action.username)
            is RegisterAction.PasswordChange -> passwordChange(action.password)
            is RegisterAction.ConfirmPasswordChange -> confirmPasswordChange(action.confirmPassword)
            is RegisterAction.ChangePage -> changePage(action.page)
            RegisterAction.Login -> login()
            RegisterAction.Register -> register()
        }
    }

    private fun generateFamilyCode() {
        viewModelScope.launch {
            _state.update { it.copy(familyCode = "FAM-0001") }
        }
    }

    private fun familyNameChange(familyName: String) {
        _state.update { it.copy(familyName = familyName) }
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

    private fun usernameChange(username: String) {
        _state.update { it.copy(username = username.lowercase()) }
    }

    private fun passwordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    private fun confirmPasswordChange(confirmPassword: String) {
        _state.update { it.copy(confirmPassword = confirmPassword) }
    }

    private fun changePage(page: RegistrationPage) {
        when (page) {
            RegistrationPage.FamilyInformation -> familyInformation()
            RegistrationPage.MemberInformation -> memberInformation()
            RegistrationPage.AccountInformation -> accountInformation()
        }
    }

    private fun familyInformation() {
        _state.update { it.copy(page = RegistrationPage.FamilyInformation) }
    }

    private fun memberInformation() {
        val familyCode = _state.value.familyCode.trim()
        val familyName = _state.value.familyName.trim()
        var isValid = true

        _state.update {
            it.copy(
                familyCodeError = false,
                familyCodeErrorMessage = null,
                familyNameError = false,
                familyNameErrorMessage = null
            )
        }

        if (familyCode.isBlank()) {
            isValid = false
            _state.update {
                it.copy(
                    familyCodeError = true,
                    familyCodeErrorMessage = "Invalid family code."
                )
            }
        }
        if (familyName.isBlank()) {
            isValid = false
            _state.update {
                it.copy(
                    familyNameError = true,
                    familyNameErrorMessage = "Invalid family name."
                )
            }
        }

        if (isValid) {
            _state.update { it.copy(page = RegistrationPage.MemberInformation) }
            return
        }
    }

    private fun accountInformation() {
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
            _state.update { it.copy(page = RegistrationPage.AccountInformation) }
            return
        }
    }

    private fun login() {
        _state.update { it.copy(login = true) }
    }

    private fun register() {
        viewModelScope.launch {
            try {
                val username = _state.value.username.trim().lowercase()
                val password = _state.value.password.trim()
                val confirmPassword = _state.value.confirmPassword.trim()
                var isValid = true

                if (state.value.isLoading)
                    return@launch

                _state.update {
                    it.copy(
                        usernameError = false,
                        usernameErrorMessage = null,
                        passwordError = false,
                        passwordErrorMessage = null,
                        confirmPasswordError = false,
                        confirmPasswordErrorMessage = null,
                        isLoading = true,
                        message = null,
                        showMessage = false,
                        isRegistered = false
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
                    val family = Family(
                        code = _state.value.familyCode.trim(),
                        name = _state.value.familyName.trim()
                    )
                    val member = Member(
                        familyCode = _state.value.familyCode.trim(),
                        firstName = _state.value.firstName.trim(),
                        lastName = _state.value.lastName.trim(),
                        middleName = _state.value.middleName.trim(),
                        maidenName = _state.value.maidenName.trim()
                    )
                    val account = Account(
                        username = _state.value.username.trim(),
                        password = _state.value.password.trim()
                    )
                    val result = authRepository.register(
                        family = family,
                        member = member,
                        account = account
                    )

                    when (result) {
                        is Result.Success -> {
                            _state.update { it.copy(isRegistered = true) }
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
                        message = "Registration failed!",
                        showMessage = true
                    )
                }
                Log.e("Registration", "Failed. Error: ${e.message}")
                e.printStackTrace()
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}