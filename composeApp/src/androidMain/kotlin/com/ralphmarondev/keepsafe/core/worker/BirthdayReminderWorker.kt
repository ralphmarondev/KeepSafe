package com.ralphmarondev.keepsafe.core.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ralphmarondev.keepsafe.core.data.local.preferences.AppPreferences
import com.ralphmarondev.keepsafe.core.util.formatBirthdayDisplay
import com.ralphmarondev.keepsafe.core.util.getDaysUntilNextBirthday
import com.ralphmarondev.keepsafe.core.util.getNotificationService
import com.ralphmarondev.keepsafe.family.domain.usecase.GetFamilyMembersUseCase
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BirthdayReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val getFamilyMembersUseCase: GetFamilyMembersUseCase by inject()
    private val preferences: AppPreferences by inject()

    override suspend fun doWork(): Result {
        val familyId = preferences.familyId().first() ?: return Result.success()
        val uid = preferences.uid().first() ?: return Result.success()

        val response = getFamilyMembersUseCase(familyId = familyId)

        if (response.success && !response.data.isNullOrEmpty()) {
            val filtered = response.data.filter { it.uid != uid }
            filtered.forEach { member ->
                val days = getDaysUntilNextBirthday(member.birthday ?: "")
                val message = formatBirthdayDisplay(member.birthday ?: "", days)
                if (message != null) {
                    getNotificationService().showNotification(
                        title = "Birthday Reminder",
                        message = message
                    )
                }
            }
        }
        return Result.success()
    }
}