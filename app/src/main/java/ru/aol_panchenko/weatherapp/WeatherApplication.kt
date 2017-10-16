package ru.aol_panchenko.weatherapp

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import ru.aol_panchenko.weatherapp.di.*

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
                .mapperModule(MapperModule())
                .daoModule(DaoModule())
                .build()
    }
}
