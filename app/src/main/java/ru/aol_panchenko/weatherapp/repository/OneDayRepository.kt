package ru.aol_panchenko.weatherapp.repository

import io.reactivex.Observable
import ru.aol_panchenko.weatherapp.WeatherApplication
import ru.aol_panchenko.weatherapp.network.ApiWeather
import ru.aol_panchenko.weatherapp.network.model.one_day.WeatherOneDayResponse
import javax.inject.Inject

/**
 * Created by Panchenko.AO on 19.09.2017.
 */
class OneDayRepository {

    @Inject lateinit var _api: ApiWeather

    init {
        WeatherApplication.appComponent.inject(this)
    }

    fun getWeatherOneDayByCityName(cityName: String, apiKey: String): Observable<WeatherOneDayResponse> {
        return _api.getWeatherOneDayByCityName(cityName, apiKey = apiKey)
    }
}