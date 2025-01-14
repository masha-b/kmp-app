package com.jetbrains.kmpapp.domain.usecases.apps

import com.jetbrains.kmpapp.domain.models.apps.VkpApp
import com.jetbrains.kmpapp.domain.repo.AppsRepo

class GetAppsByTypeUseCase (private val repo: AppsRepo) {

    suspend fun invoke(type: String): Result<List<VkpApp>> = repo.getAppsByType(type)

}