package com.jetbrains.kmpapp.domain.exceptions


class ValidationException(
    val code: String? = null,
    val error: String? = null,
    val fieldErrors: List<FieldError> = emptyList()
) : Throwable()