package com.ralphmarondev.keepsafe.reminder.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.core.util.formatBirthdayDisplay
import com.ralphmarondev.keepsafe.core.util.getDaysUntilNextBirthday
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.usecase.GetFamilyMembersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReminderViewModel(
    private val preferences: AppPreferences,
    private val getFamilyMembersUseCase: GetFamilyMembersUseCase
) : ViewModel() {

    private val _familyMembers = MutableStateFlow<List<FamilyMember>>(emptyList())
    val familyMember = _familyMembers.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        if (_isRefreshing.value) {
            return
        }
        _isRefreshing.value = true
        viewModelScope.launch {
            val familyId = preferences.familyId().first() ?: ""
            val uid = preferences.uid().first() ?: ""

            val response = getFamilyMembersUseCase(familyId = familyId)
            if (response.data?.isEmpty() == false && response.success) {
                val filteredMembers = response.data
                    .filter { it.uid != uid }
                    .mapNotNull { member ->
                        val originalBirthday = member.birthday ?: return@mapNotNull null
                        val days = getDaysUntilNextBirthday(originalBirthday)
                        val displayBirthday =
                            formatBirthdayDisplay(originalBirthday, days) ?: return@mapNotNull null
                        member.copy(birthday = displayBirthday) to days
                    }
                    .sortedBy { it.second }
                    .map { it.first }

                _familyMembers.value = filteredMembers
            } else {
                _familyMembers.value = emptyList()
            }
            _response.value = Result(
                success = response.success,
                message = response.message
            )
            _isRefreshing.value = false
        }
    }
}