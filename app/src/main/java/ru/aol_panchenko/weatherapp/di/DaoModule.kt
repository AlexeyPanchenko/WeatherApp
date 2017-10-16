package ru.aol_panchenko.weatherapp.di

import dagger.Module
import dagger.Provides
import ru.aol_panchenko.weatherapp.dao.WeatherDatabase
import javax.inject.Singleton

/**
 * Created by alexey on 16.10.17.
 */
@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherDatabase) = database.weatherDao()
}