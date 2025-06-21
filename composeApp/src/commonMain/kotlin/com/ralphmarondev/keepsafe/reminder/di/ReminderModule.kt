package com.ralphmarondev.keepsafe.reminder.di

import com.ralphmarondev.keepsafe.reminder.presentation.ReminderViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val reminderModule = module {
    viewModelOf(::ReminderViewModel)
}