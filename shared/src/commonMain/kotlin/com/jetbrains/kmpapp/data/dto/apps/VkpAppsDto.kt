package com.jetbrains.kmpapp.data.dto.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VkpAppsDto(
    val list: List<VkpAppDto>
)

/**
 * Vkp app response from VkpApps API
 *
 * @property build build number.
 * @property icon https://apps.sitesoft.ru/{icon}
 * @property link link to latest apk build
 * @property name app name
 * @property updatedAt last update time UNIX
 */
@Serializable
data class VkpAppDto(
    val build: Int,
    val host: String,
    val icon: String,
    val id: Long,
    val link: String,
    val name: String,
    val text: String?,
    @SerialName("updated_at")
    val updatedAt: Long,
    val version: String
)