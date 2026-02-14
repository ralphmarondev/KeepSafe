package com.ralphmarondev.keepsafe.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.FamilyIdChange -> {
                _state.update { current ->
                    val isValid = action.familyId.trim().length >= 8 && action.familyId.isNotBlank()
                    current.copy(
                        familyId = action.familyId,
                        isValidFamilyId = isValid,
                        familyIdSupportingText = if (!isValid) {
                            "Family Id must be at least 8 characters."
                        } else {
                            ""
                        }
                    )
                }
            }

            is LoginAction.EmailChange -> {
                _state.update { current ->
                    val isValid = action.email.contains("@") && action.email.isNotBlank()
                    current.copy(
                        email = action.email,
                        isValidEmail = isValid,
                        emailSupportingText = if (!isValid) {
                            "Please enter valid email address."
                        } else {
                            ""
                        }
                    )
                }
            }

            is LoginAction.PasswordChange -> {
                _state.update { current ->
                    val isValid = action.password.trim().length >= 8 && action.password.isNotBlank()
                    current.copy(
                        password = action.password,
                        isValidPassword = isValid,
                        passwordSupportingText = if (!isValid) {
                            "Password must be at least 8 characters."
                        } else {
                            ""
                        }
                    )
                }
            }

            LoginAction.Login -> {
                login()
            }

            LoginAction.Register -> {
                _state.update {
                    it.copy(register = true)
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            try {
                val familyId = _state.value.familyId.trim()
                val email = _state.value.email.trim()
                val password = _state.value.password.trim()

                val isValidFamilyId = familyId.length >= 8 && familyId.isNotBlank()
                val isValidEmail = email.contains("@") && email.isNotBlank()
                val isValidPassword = password.trim().length >= 8 && password.isNotBlank()

                if (!isValidFamilyId || !isValidEmail || !isValidPassword) {
                    _state.update {
                        it.copy(
                            isValidFamilyId = isValidFamilyId,
                            familyIdSupportingText = if (!isValidFamilyId) "Family Id must be at least 8 characters." else "",
                            isValidEmail = isValidEmail,
                            emailSupportingText = if (!isValidEmail) "Please enter valid email address." else "",
                            isValidPassword = isValidPassword,
                            passwordSupportingText = if (!isValidPassword) "Password must be at least 8 characters." else ""
                        )
                    }
                    return@launch
                }

                _state.update { it.copy(isLoggingIn = true, errorMessage = null) }

                val result = repository.login(
                    familyId = familyId,
                    email = email,
                    password = password
                )

                delay(2000)
                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoggingIn = false,
                                isLoggedIn = true
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isLoggingIn = false,
                                errorMessage = result.message ?: "Login failed. Please try again."
                            )
                        }
                    }

                    Result.Loading -> {
                        _state.update {
                            it.copy(isLoggingIn = true)
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoggingIn = false,
                        errorMessage = e.message ?: "Login failed. Please try again."
                    )
                }
            }
        }
    }
}