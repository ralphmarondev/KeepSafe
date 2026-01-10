package com.ralphmarondev.keepsafe.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.domain.model.Role
import com.ralphmarondev.keepsafe.core.domain.model.User
import com.ralphmarondev.keepsafe.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.FamilyIdChange -> {
                val isValid = action.familyId.isNotEmpty() && action.familyId.length >= 8
                _state.update { current ->
                    current.copy(
                        familyId = action.familyId.lowercase(),
                        isValidFamilyId = isValid,
                        familyIdSupportingText = "Family must be at least 8 characters.",
                        isFamilyIdChangedManually = true
                    )
                }
            }

            is RegisterAction.FamilyNameChange -> {
                _state.update { current ->
                    val familyId = _state.value.familyId
                    val isValidFamilyName = action.familyName.isNotEmpty()
                    val isValidFamilyId = familyId.isNotEmpty() && familyId.length >= 8

                    if (isValidFamilyName && !_state.value.isFamilyIdChangedManually) {
                        val generatedId = action.familyName.lowercase() + "-family"
                        current.copy(familyId = generatedId)
                    } else {
                        current
                    }.copy(
                        familyName = action.familyName,
                        isValidFamilyName = isValidFamilyName,
                        familyNameSupportingText = if (!isValidFamilyName) {
                            "Family name cannot be empty."
                        } else {
                            ""
                        },
                        isValidFamilyId = isValidFamilyId,
                        familyIdSupportingText = if (!isValidFamilyId) {
                            "Family Id must be at least 8 characters."
                        } else {
                            ""
                        }
                    )
                }
            }

            is RegisterAction.UsernameChange -> {
                _state.update { current ->
                    val isValid = action.username.isNotEmpty() && action.username.length >= 5
                    current.copy(
                        username = action.username,
                        isValidUsername = isValid,
                        usernameSupportingText = if (!isValid) {
                            "Username must be at least 5 characters."
                        } else {
                            ""
                        },
                        email = action.username.trim().lowercase() + "@keepsafe.com"
                    )
                }
            }

            is RegisterAction.PasswordChange -> {
                _state.update { current ->
                    val isValid = action.password.isNotEmpty() && action.password.length >= 8
                    current.copy(
                        password = action.password,
                        isValidPassword = isValid,
                        passwordSupportingText = if (!isValid && action.password.isNotEmpty()) {
                            "Password must be at least 8 characters."
                        } else {
                            ""
                        }
                    )
                }
            }

            is RegisterAction.ConfirmPasswordChange -> {
                _state.update { current ->
                    val isValid = action.confirmPassword.isNotEmpty() &&
                            action.confirmPassword.length >= 8 &&
                            action.confirmPassword == _state.value.password
                    current.copy(
                        confirmPassword = action.confirmPassword,
                        isValidConfirmPassword = isValid,
                        confirmPasswordSupportingText = if (!isValid && action.confirmPassword.isNotEmpty()) {
                            "Password did not match."
                        } else {
                            ""
                        }
                    )
                }
            }

            RegisterAction.Register -> {
                register()
            }

            RegisterAction.NavigateToLogin -> {
                _state.update { it.copy(navigateToLogin = true) }
            }

            RegisterAction.ClearNavigation -> {
                _state.update { it.copy(navigateToLogin = false) }
            }

            RegisterAction.DecrementCurrentPage -> {
                _state.update { it.copy(currentPage = it.currentPage - 1) }
            }

            RegisterAction.IncrementCurrentPage -> {
                when (_state.value.currentPage) {
                    0 -> {
                        val familyId = _state.value.familyId.trim()
                        val isValidFamilyName = _state.value.familyName.trim().isNotEmpty()
                        val isValidFamilyId = familyId.isNotEmpty() && familyId.length >= 8
                        _state.update {
                            it.copy(
                                isValidFamilyId = isValidFamilyId,
                                isValidFamilyName = isValidFamilyName,
                                familyIdSupportingText = if (!isValidFamilyId) {
                                    "Family Id must be at least 8 characters."
                                } else {
                                    ""
                                },
                                familyNameSupportingText = if (!isValidFamilyName) {
                                    "Family name cannot be empty."
                                } else {
                                    ""
                                }
                            )
                        }
                        if (isValidFamilyId && isValidFamilyName) {
                            _state.update { it.copy(currentPage = it.currentPage + 1) }
                        } else {
                            _state.update {
                                it.copy(
                                    isError = true,
                                    errorMessage = if (!isValidFamilyId && !isValidFamilyName) {
                                        "Invalid family Id and family name."
                                    } else if (isValidFamilyId) {
                                        _state.value.familyIdSupportingText
                                    } else {
                                        _state.value.familyNameSupportingText
                                    }
                                )
                            }
                        }
                    }

                    1 -> {
                        val username = _state.value.username.trim()
                        val password = _state.value.password.trim()
                        val confirmPassword = _state.value.confirmPassword.trim()

                        val isValidUsername = username.isNotEmpty() && username.length >= 5
                        val isValidPassword = password.isNotEmpty() && password.length >= 8
                        val isValidConfirmPassword = confirmPassword.isNotEmpty() &&
                                confirmPassword == password

                        _state.update {
                            it.copy(
                                isValidUsername = isValidUsername,
                                isValidPassword = isValidPassword,
                                isValidConfirmPassword = isValidConfirmPassword,
                                usernameSupportingText = if (!isValidUsername) {
                                    "Username must be at least 5 characters."
                                } else {
                                    ""
                                },
                                passwordSupportingText = if (!isValidPassword) {
                                    "Password must be at least 8 characters."
                                } else {
                                    ""
                                },
                                confirmPasswordSupportingText = if (!isValidConfirmPassword) {
                                    "Password did not match."
                                } else {
                                    ""
                                }
                            )
                        }
                        if (isValidUsername && isValidPassword && isValidConfirmPassword) {
                            _state.update { it.copy(currentPage = it.currentPage + 1) }
                        } else {
                            _state.update {
                                it.copy(
                                    isError = true,
                                    errorMessage = "Invalid credentials."
                                )
                            }
                        }
                    }

                    else -> {
                        _state.update { it.copy(currentPage = it.currentPage + 1) }
                    }
                }
            }

            RegisterAction.ShowNavigateBackDialog -> {
                _state.update { it.copy(showNavigateBackDialog = true) }
            }

            RegisterAction.CloseNavigateBackDialog -> {
                _state.update { it.copy(showNavigateBackDialog = false) }
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isRegistering = true, isRegistered = false) }

            val user = User(
                familyId = _state.value.familyId.trim(),
                familyName = _state.value.familyName.trim(),
                username = _state.value.username.trim(),
                password = _state.value.password.trim(),
                role = Role.FAMILY_ADMIN.name,
                active = true,
                firstName = "Family Admin",
                email = _state.value.email.trim()
            )

            val result = repository.register(user)
            if (result.isSuccess) {
                _state.update { it.copy(isRegistered = true, isRegistering = false) }
                delay(3000)
                _state.update { it.copy(navigateToLogin = true) }
            } else {
                _state.update {
                    it.copy(
                        isError = true,
                        isRegistering = false,
                        errorMessage = "Registering family failed."
                    )
                }
            }
        }
    }

    private fun generateFamilyId(familyName: String): String {
        val cleanedName = familyName.filter { it.isLetterOrDigit() }
        val base = when {
            cleanedName.length >= 3 -> cleanedName.take(3)
            cleanedName.length == 2 -> cleanedName + ('A'..'Z').random()
            cleanedName.length == 1 -> cleanedName + (1..2).map { ('A'..'Z').random() }
                .joinToString("")

            else -> (1..3).map { ('A'..'Z').random() }.joinToString("")
        }
        val chars = ('A'..'Z') + ('0'..'9')

        val suffix = (1..3)
            .map { chars.random() }
            .joinToString("")
        return "$base$suffix".lowercase()
    }
}