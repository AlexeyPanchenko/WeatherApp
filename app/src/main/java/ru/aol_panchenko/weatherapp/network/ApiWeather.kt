package ru.aol_panchenko.weatherapp.network

import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.aol_panchenko.weatherapp.API_KEY
import ru.aol_panchenko.weatherapp.network.model.one_day.WeatherOneDayResponse

/**
 * Created by alexey on 17.09.17.
 */
interface ApiWeather {

    @GET("weather")
    fun getWeatherOneDayByCityName(
            @Query("q") cityName: String,
            @Query("units") units: String = "metric",
            @Query("appid") apiKey: String = API_KEY): Flowable<WeatherOneDayResponse>

    @GET("weather")
    fun getWeatherOneDayByGeo(
            @Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("units") units: String = "metric",
            @Query("appid") apiKey: String = API_KEY): Flowable<WeatherOneDayResponse>
}