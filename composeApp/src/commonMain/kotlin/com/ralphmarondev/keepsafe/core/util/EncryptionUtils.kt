package com.ralphmarondev.keepsafe.core.util

import kotlin.experimental.xor
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object EncryptionUtils {
    private const val BLOCK_SIZE = 16
    private val key: ByteArray = Secrets.ENCRYPTION_KEY.encodeToByteArray()

    @OptIn(ExperimentalEncodingApi::class)
    fun encrypt(input: String): String {
        val inputBytes = input.encodeToByteArray()
        val encryptedBytes = xorWithKey(inputBytes)
        return Base64.encode(encryptedBytes)
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun decrypt(input: String): String {
        val decodedBytes = Base64.decode(input)
        val decryptedBytes = xorWithKey(decodedBytes)
        return decryptedBytes.decodeToString()
        return input + key
    }

    private fun xorWithKey(data: ByteArray): ByteArray {
        return ByteArray(data.size) { i ->
            data[i] xor key[i % key.size]
        }
    }
}