package com.jetbrains.kmpapp.domain.exceptions

data class FieldError(
    val field: String,
    val errorText: String
)