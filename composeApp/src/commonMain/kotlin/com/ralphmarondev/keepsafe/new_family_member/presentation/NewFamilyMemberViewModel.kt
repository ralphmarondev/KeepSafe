package com.ralphmarondev.keepsafe.new_family_member.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.new_family_member.data.network.NewFamilyMemberApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NewFamilyMemberViewModel(
    private val newFamilyMemberApiService: NewFamilyMemberApiService,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private val _role = MutableStateFlow("")
    val role = _role.asStateFlow()

    private val _birthday = MutableStateFlow("")
    val birthday = _birthday.asStateFlow()

    private val _birthplace = MutableStateFlow("")
    val birthplace = _birthplace.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


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

    fun register() {
        if (_fullName.value.isBlank()) {
            return
        }
        if (_email.value.isBlank()) {
            return
        }
        if (_role.value.isBlank()) {
            return
        }
        if (_birthplace.value.isBlank()) {
            return
        }
        if (_birthday.value.isBlank()) {
            return
        }
        if (_password.value.isBlank()) {
            return
        }

        viewModelScope.launch {
            val familyId = preferences.familyId().first()

            newFamilyMemberApiService.registerNewFamilyMember(
                fullName = _fullName.value.trim(),
                email = _email.value.trim(),
                role = _role.value.trim(),
                birthplace = _birthplace.value.trim(),
                birthday = _birthday.value.trim(),
                familyId = familyId ?: "No family id provided.",
                password = _password.value.trim()
            )
        }
    }
}