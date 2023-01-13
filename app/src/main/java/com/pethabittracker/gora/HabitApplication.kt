package com.pethabittracker.gora

import android.app.Application
import com.pethabittracker.gora.data.di.dataModule
import com.pethabittracker.gora.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HabitApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HabitApplication)
            modules(
                dataModule,
                viewModelModule
            )
        }
    }
}
