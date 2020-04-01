package com.icongkhanh.common

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    object Error : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Result.Success -> "Success: ${data}"
            is Result.Error -> "Error"
            else -> "Loading"
        }
    }
}

fun Result<*>.isSuccess() = this is Result.Success && this.data != null