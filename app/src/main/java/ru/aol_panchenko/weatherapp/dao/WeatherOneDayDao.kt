package ru.aol_panchenko.weatherapp.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import ru.aol_panchenko.weatherapp.presentation.model.Weather

/**
 * Created by alexey on 16.10.17.
 */
@Dao
interface WeatherOneDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(weather: Weather)

    @Delete
    fun remove(weather: Weather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(weathers: List<Weather>)

    @Query("SELECT * FROM weathers_one_day")
    fun getAll(): Flowable<List<Weather>>

    @Query("SELECT * FROM weathers_one_day WHERE cityName LIKE :cityName")
    fun findByCityName(cityName:String): Single<Weather>

    @Query("SELECT cityName FROM weathers_one_day")
    fun getAllCity(): List<String>
}