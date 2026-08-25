package com.ralphmarondev.keepsafe.feature.auth.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.domain.model.Result
import com.ralphmarondev.keepsafe.domain.repository.AuthRepository
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
            is LoginAction.FamilyCodeChange -> familyCodeChange(action.code)
            is LoginAction.UsernameChange -> usernameChange(action.username)
            is LoginAction.PasswordChange -> passwordChange(action.password)
            LoginAction.Register -> register()
            LoginAction.Login -> login()
        }
    }

    private fun familyCodeChange(code: String) {
        _state.update { it.copy(familyCode = code) }
    }

    private fun usernameChange(username: String) {
        _state.update { it.copy(username = username) }
    }

    private fun passwordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    private fun register() {
        _state.update { it.copy(navigateToRegister = true) }
    }

    private fun login() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoggingIn = true,
                        isLoggedIn = false,
                        familyCodeError = false,
                        familyCodeErrorMessage = null,
                        usernameError = false,
                        usernameErrorMessage = null,
                        passwordError = false,
                        passwordErrorMessage = null
                    )
                }
                val familyCode = _state.value.familyCode.trim()
                val username = _state.value.username.trim()
                val password = _state.value.password.trim()
                var isValid = true

                if (familyCode.isBlank()) {
                    isValid = false
                    _state.update {
                        it.copy(
                            familyCodeError = true,
                            familyCodeErrorMessage = "Family code cannot be empty."
                        )
                    }
                }

                if (username.isBlank()) {
                    isValid = false
                    _state.update {
                        it.copy(
                            usernameError = true,
                            usernameErrorMessage = "Username cannot be empty."
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

                if (!isValid) {
                    return@launch
                }

                val result = repository.login(
                    username = "$username@keepsafe.com",
                    password = password,
                    familyCode = familyCode
                )

                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(isLoggedIn = true) }
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                familyCodeError = true,
                                familyCodeErrorMessage = "Invalid credentials.",
                                usernameError = true,
                                usernameErrorMessage = "Invalid credentials",
                                passwordError = true,
                                passwordErrorMessage = "Invalid credentials."
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        familyCodeError = true,
                        familyCodeErrorMessage = "Invalid credentials.",
                        usernameError = true,
                        usernameErrorMessage = "Invalid credentials",
                        passwordError = true,
                        passwordErrorMessage = "Invalid credentials."
                    )
                }
                Log.e("Login", "Login failed. Error: ${e.message}")
                e.printStackTrace()
            } finally {
                _state.update { it.copy(isLoggingIn = false) }
            }
        }
    }
}