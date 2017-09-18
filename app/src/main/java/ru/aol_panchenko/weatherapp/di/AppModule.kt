package ru.aol_panchenko.weatherapp.di

import dagger.Module
import dagger.Provides
import ru.aol_panchenko.weatherapp.WeatherApplication
import ru.aol_panchenko.weatherapp.network.RetrofitBuilder
import javax.inject.Singleton

/**
 * Created by alexey on 18.09.17.
 */
@Module
class AppModule(private val application: WeatherApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext() = application

    @Provides
    @Singleton
    fun provideRetrofitApi() = RetrofitBuilder.create()
}