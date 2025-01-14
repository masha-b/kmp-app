package com.jetbrains.kmpapp.domain.repo

import com.jetbrains.kmpapp.domain.models.apps.VkpApp


interface AppsRepo {
    suspend fun getAppsByType(type: String): Result<List<VkpApp>>
}