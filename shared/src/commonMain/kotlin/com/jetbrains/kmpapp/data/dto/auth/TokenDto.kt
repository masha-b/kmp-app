package com.jetbrains.kmpapp.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class AuthResultDto(
    @SerialName("error_code")
    val errorCode: Int,
    @SerialName("error_name")
    val errorName: String,
    val data: JsonElement
) {
    fun getToken(): String {
        return when (data) {
            is JsonObject -> data.jsonObject["token"]?.jsonPrimitive?.content ?: ""
            is JsonPrimitive -> data.content
            else -> ""
        }
    }
}