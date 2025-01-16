package com.jetbrains.kmpapp

import android.app.Application
import com.jetbrains.kmpapp.di.initKoin
import com.jetbrains.kmpapp.presentation.AppViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            appDeclaration = { androidContext(this@App) },
            extraModules = listOf(
                module {
                    single<AppViewModel> { AppViewModel() }
                }
            )
        )
    }
}
