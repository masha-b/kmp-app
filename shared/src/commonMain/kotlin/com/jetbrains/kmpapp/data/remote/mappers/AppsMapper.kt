package com.jetbrains.kmpapp.data.remote.mappers

import com.jetbrains.kmpapp.data.dto.apps.VkpAppDto
import com.jetbrains.kmpapp.domain.models.apps.VkpApp
import kotlin.collections.map

object AppsMapper {

    fun List<VkpAppDto>.toDomain(): List<VkpApp> = map { it.toDomain() }

    private fun VkpAppDto.toDomain(): VkpApp = VkpApp(
        id = id,
        build = build,
        host = host,
        icon = icon,
        link = link,
        name = name,
        text = text,
        updatedAt = updatedAt,
        version = version
    )
}