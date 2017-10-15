package ru.aol_panchenko.weatherapp.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import ru.aol_panchenko.weatherapp.presentation.model.Weather

/**
 * Created by alexey on 16.10.17.
 */
@Dao
interface WeatherDao {
    @Insert
    fun save(weather: Weather)

    @Query("select * from weathers_one_day")
    fun getAll(): Flowable<List<Weather>>
}