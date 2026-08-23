package com.ralphmarondev.keepsafe.feature.auth.presentation.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.FamilyCodeChange -> familyCodeChange(action.code)
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

    private fun familyCodeChange(familyCode: String) {
        _state.update { it.copy(familyCode = familyCode) }
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
        _state.update { it.copy(username = username) }
    }

    private fun passwordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    private fun confirmPasswordChange(confirmPassword: String) {
        _state.update { it.copy(confirmPassword = confirmPassword) }
    }

    private fun changePage(page: RegistrationPage) {
        _state.update { it.copy(page = page) }
    }

    private fun login() {
        _state.update { it.copy(login = true) }
    }

    private fun register() {

    }
}