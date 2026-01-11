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
                _state.update { it.copy(isLoggingIn = true, errorMessage = null) }

                if (!_state.value.isValidEmail || !_state.value.isValidPassword || !_state.value.isValidFamilyId) {
                    _state.update {
                        it.copy(
                            familyIdSupportingText = if (!_state.value.isValidFamilyId) "Please enter a valid family id" else it.emailSupportingText,
                            emailSupportingText = if (!_state.value.isValidEmail) "Please enter a valid email address" else it.emailSupportingText,
                            passwordSupportingText = if (!_state.value.isValidPassword) "Password must be at least 8 characters" else it.passwordSupportingText,
                            errorMessage = "Invalid credentials."
                        )
                    }
                    return@launch
                }

                val result = repository.login(
                    familyId = _state.value.familyId.trim(),
                    email = _state.value.email.trim(),
                    password = _state.value.password.trim()
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