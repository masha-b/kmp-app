package com.jetbrains.kmpapp.data.dto.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer

@Serializable
class ServerResponse<T>(
    @SerialName("error_code")
    val errorCode: Int,
    @SerialName("error_name")
    val errorName: String,
    @SerialName("data")
    val jsonData: JsonElement,
) {
    val isSuccessful: Boolean
        get() = errorCode == SUCCESS_CODE

    inline fun <reified T> getData(): T? {
        return jsonData.takeIf { isSuccessful && it is JsonObject }
            ?.let { Json.decodeFromJsonElement(serializer(), it) }
    }

    private companion object {
        const val SUCCESS_CODE = 0
    }
}