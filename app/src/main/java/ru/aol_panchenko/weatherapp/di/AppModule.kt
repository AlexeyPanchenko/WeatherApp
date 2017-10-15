package ru.aol_panchenko.weatherapp.di

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.aol_panchenko.weatherapp.WeatherApplication
import ru.aol_panchenko.weatherapp.dao.WeatherDao
import ru.aol_panchenko.weatherapp.dao.WeatherDatabase
import ru.aol_panchenko.weatherapp.dao.WeatherOneDayDao
import ru.aol_panchenko.weatherapp.network.ApiWeather
import ru.aol_panchenko.weatherapp.network.RetrofitBuilder
import ru.aol_panchenko.weatherapp.repository.OneDayRepository
import ru.aol_panchenko.weatherapp.repository.WeatherMapper
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

    @Provides
    @Singleton
    fun provideOneWeatherRepository(api: ApiWeather, mapper: WeatherMapper, dao: WeatherDao)
            = OneDayRepository(api, mapper, dao)

    @Provides
    @Singleton
    fun provideRealmConfig(): RealmConfiguration {
        Realm.init(application)
        return RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
    }

    @Provides
    @Singleton
    fun provideRealm(configuration: RealmConfiguration) = Realm.getInstance(configuration)

    @Provides
    @Singleton
    fun provideDatabase() = Room.databaseBuilder(application, WeatherDatabase::class.java, "weather-db").build()
}