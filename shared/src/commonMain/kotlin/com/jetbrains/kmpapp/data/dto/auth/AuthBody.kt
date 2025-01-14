package com.jetbrains.kmpapp.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthBody(
    val email: String,
    val password: String
)