package com.ralphmarondev.keepsafe.family.presentation.update_member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.usecase.GetDetailsUseCase
import com.ralphmarondev.keepsafe.family.domain.usecase.UpdateFamilyMemberUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateFamilyMemberViewModel(
    private val uid: String,
    private val getDetailsUseCase: GetDetailsUseCase,
    private val updateFamilyMemberUseCase: UpdateFamilyMemberUseCase
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

    private val _familyId = MutableStateFlow("")

    init {
        viewModelScope.launch {
            val result: FamilyMember? = getDetailsUseCase(uid = uid)

            _fullName.value = result?.fullName ?: ""
            _role.value = result?.role ?: ""
            _birthday.value = result?.birthday ?: ""
            _birthplace.value = result?.birthplace ?: ""
            _email.value = result?.email ?: ""
            _familyId.value = result?.familyId ?: ""
        }
    }

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

    fun update() {
        viewModelScope.launch {
            val result = updateFamilyMemberUseCase(
                familyMember = FamilyMember(
                    uid = uid,
                    fullName = _fullName.value.trim(),
                    email = _email.value.trim(),
                    birthplace = _birthplace.value.trim(),
                    birthday = _birthday.value.trim(),
                    role = _role.value.trim(),
                    familyId = _familyId.value.trim()
                )
            )

            _response.value = if (result) {
                Result(
                    success = true,
                    message = "Updated successfully."
                )
            } else {
                Result(
                    success = false,
                    message = "Update failed."
                )
            }
        }
    }
}