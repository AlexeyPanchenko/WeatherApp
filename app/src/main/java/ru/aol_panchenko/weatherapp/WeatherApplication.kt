package ru.aol_panchenko.weatherapp

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import ru.aol_panchenko.weatherapp.di.AppComponent
import ru.aol_panchenko.weatherapp.di.AppModule
import ru.aol_panchenko.weatherapp.di.DaggerAppComponent

/**
 * Created by alexey on 17.09.17.
 */

class WeatherApplication : Application() {

    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }
}
