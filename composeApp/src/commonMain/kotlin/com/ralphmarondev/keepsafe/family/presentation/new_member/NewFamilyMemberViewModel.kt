package com.ralphmarondev.keepsafe.family.presentation.new_member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.family.domain.model.NewFamilyMember
import com.ralphmarondev.keepsafe.family.domain.usecase.AddNewFamilyMemberUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewFamilyMemberViewModel(
    private val newFamilyMemberUseCase: AddNewFamilyMemberUseCase
) : ViewModel() {

    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private val _role = MutableStateFlow("Member")
    val role = _role.asStateFlow()

    private val _birthday = MutableStateFlow("")
    val birthday = _birthday.asStateFlow()

    private val _birthplace = MutableStateFlow("")
    val birthplace = _birthplace.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()


    fun onFullNameValueChange(value: String) {
        _fullName.value = value
    }

    fun onRoleValueChange(value: String) {
        _role.value = value
    }

    fun onBirthdayValueChange(value: String) {
        _birthday.value = value
    }

    fun onBirthplaceValueChange(value: String) {
        _birthplace.value = value
    }

    fun onEmailValueChange(value: String) {
        _email.value = value
    }

    fun onPasswordValueChange(value: String) {
        _password.value = value
    }

    fun clearResponse() {
        _response.value = null
    }

    fun register() {
        viewModelScope.launch {
            val response: Result = newFamilyMemberUseCase(
                newFamilyMember = NewFamilyMember(
                    fullName = _fullName.value.trim(),
                    email = _email.value.trim(),
                    role = _role.value.trim(),
                    birthplace = _birthplace.value.trim(),
                    birthday = _birthday.value.trim(),
                    password = _password.value.trim()
                )
            )
            _response.value = response
            println("Response: $response")

            if (response.success) {
                _fullName.value = ""
                _email.value = ""
                _role.value = ""
                _birthplace.value = ""
                _birthday.value = ""
                _password.value = ""
            }
        }
    }
}