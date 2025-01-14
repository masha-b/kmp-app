package com.jetbrains.kmpapp

import android.app.Application
import com.jetbrains.kmpapp.di.initKoin
import com.jetbrains.kmpapp.di.presentationModule
import com.jetbrains.kmpapp.presentation.AppViewModel
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            listOf(
                presentationModule,
                module {
                    single<AppViewModel> { AppViewModel() }
                }
            )
        )
    }
}
