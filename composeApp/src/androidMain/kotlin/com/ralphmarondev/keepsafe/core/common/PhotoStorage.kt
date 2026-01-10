package com.ralphmarondev.keepsafe.core.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object PhotoStorage {
    fun saveBitmap(
        context: Context,
        bitmap: Bitmap,
        fileName: String
    ): String {
        val file = File(context.filesDir, fileName)
        file.parentFile?.mkdirs()
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        return file.absolutePath
    }

    fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
        } catch (_: Exception) {
            null
        }
    }

    fun deletePhoto(context: Context, fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) file.delete() else false
    }

    fun exists(context: Context, fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists()
    }
}