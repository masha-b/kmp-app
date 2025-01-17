package com.jetbrains.kmpapp.domain.usecases.apps

import com.jetbrains.kmpapp.domain.models.apps.VkpBuild
import com.jetbrains.kmpapp.domain.repo.AppsRepo

class GetBuildsByAppIdUseCase (private val repo: AppsRepo) {

    suspend fun invoke(appId: Long): Result<List<VkpBuild>> = repo.getBuildsByAppId(appId)

}