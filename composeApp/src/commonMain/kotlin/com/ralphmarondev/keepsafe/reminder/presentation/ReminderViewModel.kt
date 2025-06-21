package com.ralphmarondev.keepsafe.reminder.presentation

import androidx.lifecycle.ViewModel
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReminderViewModel : ViewModel() {

    private val _reminders = MutableStateFlow<List<FamilyMember>>(emptyList())
    val reminders = _reminders.asStateFlow()

}