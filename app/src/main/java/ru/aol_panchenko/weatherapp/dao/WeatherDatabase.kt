package ru.aol_panchenko.weatherapp.dao

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import ru.aol_panchenko.weatherapp.presentation.model.Weather


/**
 * Created by alexey on 16.10.17.
 */
@Database(entities = arrayOf(Weather::class), version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherOneDayDao
}