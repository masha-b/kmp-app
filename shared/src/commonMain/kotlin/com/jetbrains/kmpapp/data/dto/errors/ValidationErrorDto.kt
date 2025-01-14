package com.jetbrains.kmpapp.data.dto.errors

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidationErrorDto(
    @SerialName("errors")
    val errors: List<FieldErrorDto>
)