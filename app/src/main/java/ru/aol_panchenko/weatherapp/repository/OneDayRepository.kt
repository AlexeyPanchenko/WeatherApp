package ru.aol_panchenko.weatherapp.repository

import io.reactivex.Observable
import ru.aol_panchenko.weatherapp.API_KEY
import ru.aol_panchenko.weatherapp.network.ApiWeather
import ru.aol_panchenko.weatherapp.network.model.one_day.WeatherOneDayResponse
import ru.aol_panchenko.weatherapp.presentation.model.Weather
import javax.inject.Inject

/**
 * Created by Panchenko.AO on 19.09.2017.
 */
class OneDayRepository @Inject constructor(val api: ApiWeather, val weatherMapper: WeatherMapper) {

    fun getWeatherOneDayByCityName(cityName: String): Observable<Weather> {
        return api.getWeatherOneDayByCityName(cityName, apiKey = API_KEY)
                .flatMap { dtoWeatherToWeather(it, cityName) }
    }

    private fun dtoWeatherToWeather(response: WeatherOneDayResponse, cityName: String) =
            weatherMapper.dtoToPresentation(response, cityName)
}