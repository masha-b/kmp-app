package com.jetbrains.kmpapp.data.dto.errors

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerErrorDto(
    @SerialName("message")
    val errorText: String?
)