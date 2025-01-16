package com.jetbrains.kmpapp.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration


fun initKoin(appDeclaration: KoinAppDeclaration, extraModules: List<Module>) =
    startKoin {
        appDeclaration()
        modules(
            localStorageModule,
            dataModule,
            domainModule,
            presentationModule,
            *extraModules.toTypedArray(),
        )
    }