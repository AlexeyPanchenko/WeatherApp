package ru.aol_panchenko.weatherapp.di

import dagger.Module
import dagger.Provides
import io.realm.Realm
import ru.aol_panchenko.weatherapp.dao.WeatherDao
import ru.aol_panchenko.weatherapp.dao.WeatherDatabase
import ru.aol_panchenko.weatherapp.dao.WeatherOneDayDao
import javax.inject.Singleton

/**
 * Created by alexey on 16.10.17.
 */
@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideWeatherOneDayDao(realm: Realm) = WeatherOneDayDao(realm)

    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherDatabase) = database.weatherDao()
}