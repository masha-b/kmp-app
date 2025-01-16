package com.jetbrains.kmpapp.di


import com.jetbrains.kmpapp.domain.usecases.apps.GetAppsByTypeUseCase
import com.jetbrains.kmpapp.domain.usecases.auth.AuthUseCase
import com.jetbrains.kmpapp.domain.usecases.auth.GetAuthStateUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule: Module
    get() = module {
        factory { AuthUseCase(get()) }
        factory { GetAuthStateUseCase(get()) }

        factory { GetAppsByTypeUseCase(get()) }
    }