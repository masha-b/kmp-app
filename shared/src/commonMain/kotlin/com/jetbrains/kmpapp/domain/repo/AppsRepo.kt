package com.jetbrains.kmpapp.domain.repo

import com.jetbrains.kmpapp.domain.models.apps.VkpApp
import com.jetbrains.kmpapp.domain.models.apps.VkpBuild


interface AppsRepo {
    suspend fun getAppsByType(type: String): Result<List<VkpApp>>

    suspend fun getBuildsByAppId(appId: Long): Result<List<VkpBuild>>
}