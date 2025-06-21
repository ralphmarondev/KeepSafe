package com.ralphmarondev.keepsafe.core.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayIn

fun monthNameToNumber(month: String): Int {
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

fun getDaysUntilNextBirthday(birthdayStr: String): Int {
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

fun formatBirthdayDisplay(birthdayStr: String?, days: Int): String? {
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