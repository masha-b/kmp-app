package com.jetbrains.kmpapp.domain

sealed class Resource<T>(val data: T? = null, val error: Throwable? = null) {
    class Success<T>(data: T, error: Throwable? = null) : Resource<T>(data = data, error = error)
    class Error<T>(error: Throwable) : Resource<T>(error = error)
}
