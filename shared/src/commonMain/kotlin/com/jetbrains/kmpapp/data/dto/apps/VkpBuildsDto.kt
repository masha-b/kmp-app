package com.jetbrains.kmpapp.data.dto.apps

import kotlinx.serialization.Serializable

@Serializable
data class VkpBuildsDto(
    val list: List<VkpBuildDto>
)

/**
 * Vkp build response from Vkp API
 *
 * @property datetime build date UNIX
 * @property link link to APK
 */
@Serializable
data class VkpBuildDto(
    val datetime: Long,
    val link: String,
    val version: Int
)