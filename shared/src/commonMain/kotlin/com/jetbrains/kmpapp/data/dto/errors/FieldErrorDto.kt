package com.jetbrains.kmpapp.data.dto.errors

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Класс для ошибок полей
 */
@Serializable
data class FieldErrorDto(
    @SerialName("field")
    val field: String,
    @SerialName("message")
    val errorText: String
)
