package com.ralphmarondev.keepsafe.core.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleBirthdayReminderWorker(context: Context) {
    val request = PeriodicWorkRequestBuilder<BirthdayReminderWorker>(
        repeatInterval = 24, // every 24 hours
        repeatIntervalTimeUnit = TimeUnit.HOURS
    ).build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "BirthdayReminderWork",
        ExistingPeriodicWorkPolicy.UPDATE,
        request
    )
}