package ru.aol_panchenko.weatherapp.di

import dagger.Module
import dagger.Provides
import ru.aol_panchenko.weatherapp.repository.WeatherMapper
import javax.inject.Singleton

/**
 * Created by Panchenko.AO on 20.09.2017.
 */
@Module
class MapperModule {

    @Provides
    @Singleton
    fun provideWeatherMapper() = WeatherMapper()
}