package com.ralphmarondev.keepsafe.reminder.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.domain.model.Result
import com.ralphmarondev.keepsafe.family.domain.model.FamilyMember
import com.ralphmarondev.keepsafe.family.domain.usecase.GetFamilyMembersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayIn

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

    private fun monthNameToNumber(month: String): Int {
        return when (month.lowercase()) {
            "january" -> 1
            "february" -> 2
            "march" -> 3
            "april" -> 4
            "may" -> 5
            "june" -> 6
            "july" -> 7
            "august" -> 8
            "september" -> 9
            "october" -> 10
            "november" -> 11
            "december" -> 12
            else -> throw IllegalArgumentException("Invalid month name: $month")
        }
    }

    private fun getDaysUntilNextBirthday(birthdayStr: String): Int {
        return try {
            val parts = birthdayStr.split(",")
            if (parts.size != 2) return Int.MAX_VALUE
            val (monthDayStr, _) = parts
            val (monthStr, dayStr) = monthDayStr.trim().split(" ")
            val month = monthNameToNumber(monthStr)
            val day = dayStr.toInt()

            val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
            val birthdayThisYear = LocalDate(today.year, month, day)

            return when {
                birthdayThisYear == today -> 0
                birthdayThisYear > today -> today.daysUntil(birthdayThisYear)
                else -> today.daysUntil(LocalDate(today.year + 1, month, day))
            }
        } catch (e: Exception) {
            Int.MAX_VALUE
        }
    }

    private fun formatBirthdayDisplay(birthdayStr: String?, days: Int): String? {
        return try {
            if (birthdayStr == null) return null
            val parts = birthdayStr.split(",")
            if (parts.size != 2) return null
            val (monthDayStr, _) = parts
            val (monthStr, dayStr) = monthDayStr.trim().split(" ")

            return when (days) {
                0 -> "Birthday today!"
                1 -> "Birthday tomorrow!"
                else -> "$monthStr $dayStr - $days day${if (days != 1) "s" else ""} before"
            }
        } catch (e: Exception) {
            null
        }
    }
}