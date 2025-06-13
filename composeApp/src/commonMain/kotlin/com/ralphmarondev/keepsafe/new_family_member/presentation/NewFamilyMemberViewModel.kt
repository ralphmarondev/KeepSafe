package com.ralphmarondev.keepsafe.new_family_member.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewFamilyMemberViewModel : ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName = _lastName.asStateFlow()

    private val _middleName = MutableStateFlow("")
    val middleName = _middleName.asStateFlow()

    private val _role = MutableStateFlow("")
    val role = _role.asStateFlow()

    private val _birthday = MutableStateFlow("")
    val birthday = _birthday.asStateFlow()

    private val _birthplace = MutableStateFlow("")
    val birthplace = _birthplace.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()


    fun onFirstNameValueChange(value: String) {
        _firstName.value = value
    }

    fun onLastNameValueChange(value: String) {
        _lastName.value = value
    }

    fun onMiddleNameValueChange(value: String) {
        _middleName.value = value
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

    fun onPhoneNumberValueChange(value: String) {
        _phoneNumber.value = value
    }

    fun register() {

    }
}