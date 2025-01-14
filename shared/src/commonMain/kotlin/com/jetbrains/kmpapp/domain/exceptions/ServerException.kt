package com.jetbrains.kmpapp.domain.exceptions


class ServerException(
    val code: String? = null,
    val error: String? = null
) : Throwable()