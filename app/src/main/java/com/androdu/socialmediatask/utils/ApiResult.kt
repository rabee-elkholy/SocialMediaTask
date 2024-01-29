package com.androdu.socialmediatask.utils

sealed class ApiResult<out T> {
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}