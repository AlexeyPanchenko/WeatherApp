package ru.aol_panchenko.weatherapp.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.aol_panchenko.weatherapp.network.model.Weather

/**
 * Created by alexey on 17.09.17.
 */
interface ApiWeather {

    @GET
    fun getWeatherByCityName(@Query("q") cityName: String): Observable<Weather>
}