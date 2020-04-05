package com.icongkhanh.common

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val err: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Result.Success -> "Success: ${data}"
            is Result.Error -> "Error: ${err.message}"
            else -> "Loading"
        }
    }
}

fun Result<*>.isSuccess() = this is Result.Success && this.data != null