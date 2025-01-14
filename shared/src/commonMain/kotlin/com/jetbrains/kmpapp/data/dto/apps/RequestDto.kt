package com.jetbrains.kmpapp.data.dto.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class RequestDto(
    @SerialName("error_code")
    val errorCode: Int,
    @SerialName("error_name")
    val errorName: String,
    val data: VkpAppsDto
) {
//    fun getToken(): String {
//        return when (data) {
//            is JsonObject -> data.jsonObject["list"]?.jsonPrimitive?.content ?: ""
//            is JsonPrimitive -> data.content
//            else -> ""
//        }
//    }
}