package com.jetbrains.kmpapp.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration


fun initKoin(appDeclaration: KoinAppDeclaration? = null, extraModules: List<Module> = emptyList()) =
    startKoin {
        appDeclaration?.let { it() }
        modules(
            localStorageModule,
            dataModule,
            domainModule,
            presentationModule,
            *extraModules.toTypedArray(),
        )
    }

fun initKoin() {
    startKoin {
        modules(
            localStorageModule,
            dataModule,
            domainModule,
            presentationModule,
        )
    }
}