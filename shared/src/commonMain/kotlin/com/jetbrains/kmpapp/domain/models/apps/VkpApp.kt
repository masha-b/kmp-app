package com.jetbrains.kmpapp.domain.models.apps


data class VkpApp(
    val build: Int,
    val host: String,
    val icon: String,
    val id: Long,
    val link: String,
    val name: String,
    val text: String?,
    val updatedAt: Long,
    val version: String
)