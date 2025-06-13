package com.ralphmarondev.keepsafe.core.data.local.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "KeepSafe")
            os.contains("mac") -> File(userHome, "Library/Application Support/KeepSafe")
            else -> File(userHome, ".local/share/KeepSafe")
        }

        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, AppDatabase.DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}