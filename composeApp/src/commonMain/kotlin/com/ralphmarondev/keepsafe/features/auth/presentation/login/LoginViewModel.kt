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
                    val isValid = action.familyId.length >= 8 && action.familyId.isNotEmpty()
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
                    val isValid = action.email.contains("@") && action.email.isNotEmpty()
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
                    val isValid = action.password.length >= 8 && action.password.isNotEmpty()
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
            val familyId = _state.value.familyId.trim()
            val email = _state.value.email.trim()
            val password = _state.value.password.trim()

            if (!_state.value.isValidEmail || !_state.value.isValidPassword || !_state.value.isValidFamilyId) {
                _state.update {
                    it.copy(
                        isError = true,
                        familyIdSupportingText = if (!_state.value.isValidFamilyId) "Please enter a valid family id" else it.emailSupportingText,
                        emailSupportingText = if (!_state.value.isValidEmail) "Please enter a valid email address" else it.emailSupportingText,
                        passwordSupportingText = if (!_state.value.isValidPassword) "Password must be at least 8 characters" else it.passwordSupportingText
                    )
                }
                return@launch
            }

            _state.update { it.copy(isLoggingIn = true, isError = false) }
            delay(2000)

            val result = repository.login(
                familyId = familyId,
                email = email,
                password = password
            )
            when (result) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoggingIn = false,
                            isLoggedIn = true,
                            isError = false,
                            errorMessage = null
                        )
                    }
                }

                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoggingIn = false,
                            isError = true,
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
        }
    }
}