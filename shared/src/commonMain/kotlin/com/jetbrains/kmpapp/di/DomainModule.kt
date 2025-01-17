package com.jetbrains.kmpapp.di


import com.jetbrains.kmpapp.domain.usecases.apps.GetAppsByTypeUseCase
import com.jetbrains.kmpapp.domain.usecases.apps.GetBuildsByAppIdUseCase
import com.jetbrains.kmpapp.domain.usecases.auth.AuthUseCase
import com.jetbrains.kmpapp.domain.usecases.auth.GetAuthStateUseCase
import com.jetbrains.kmpapp.domain.usecases.auth.LogoutUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule: Module
    get() = module {
        factory { AuthUseCase(get()) }
        factory { GetAuthStateUseCase(get()) }
        factory { LogoutUseCase(get()) }

        factory { GetAppsByTypeUseCase(get()) }
        factory { GetBuildsByAppIdUseCase(get()) }
    }