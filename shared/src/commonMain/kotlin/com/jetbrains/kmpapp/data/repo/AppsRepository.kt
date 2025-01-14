package com.jetbrains.kmpapp.data.repo

import com.jetbrains.kmpapp.data.remote.api.AppsApi
import com.jetbrains.kmpapp.data.remote.mappers.AppsMapper.toDomain
import com.jetbrains.kmpapp.domain.map
import com.jetbrains.kmpapp.domain.models.apps.VkpApp
import com.jetbrains.kmpapp.domain.repo.AppsRepo

class AppsRepository(
    private val appsApi: AppsApi
) : AppsRepo {

    override suspend fun getAppsByType(type: String): Result<List<VkpApp>> =
        appsApi.getAppsByType(type).map { it.data.list.toDomain() }
}