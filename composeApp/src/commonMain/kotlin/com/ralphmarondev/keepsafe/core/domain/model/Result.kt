package com.ralphmarondev.keepsafe.core.domain.model

data class Result(
    val success: Boolean,
    val message: String
)

data class ResultWithData<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
) {
    companion object {
        fun <T> success(message: String, data: T?): ResultWithData<T> {
            return ResultWithData(success = true, message = message, data = data)
        }

        fun <T : Any> successWithNonNullData(message: String, data: T): ResultWithData<T> {
            return ResultWithData(success = true, message = message, data = data)
        }

        fun <T> error(message: String, data: T? = null): ResultWithData<T> {
            return ResultWithData(success = false, message = message, data = data)
        }

        fun error(message: String): ResultWithData<Nothing> {
            return ResultWithData(success = false, message = message, data = null)
        }
    }
}