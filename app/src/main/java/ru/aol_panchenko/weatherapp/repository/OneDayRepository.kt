package ru.aol_panchenko.weatherapp.repository

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.Function
import ru.aol_panchenko.weatherapp.API_KEY
import ru.aol_panchenko.weatherapp.dao.WeatherOneDayDao
import ru.aol_panchenko.weatherapp.network.ApiWeather
import ru.aol_panchenko.weatherapp.network.model.one_day.WeatherOneDayResponse
import ru.aol_panchenko.weatherapp.presentation.model.Weather
import javax.inject.Inject

/**
 * Created by Panchenko.AO on 19.09.2017.
 */
class OneDayRepository @Inject constructor(val api: ApiWeather, val weatherMapper: WeatherMapper,
                                           val dao: WeatherOneDayDao) {

    fun getWeatherOneDayByCityName(cityName: String): Flowable<Weather> {
        return api.getWeatherOneDayByCityName(cityName, apiKey = API_KEY)
                .flatMap { dtoWeatherToWeather(it, cityName) }
                .doOnNext { dao.save(it) }
    }

    fun updateWeathers() {

    }

    fun getAll() = dao.getAll()

    fun findByCityName(cityName: String) = dao.findByCityName(cityName)

    fun remove(weather: Weather) = dao.remove(weather)

    fun saveAll(weathers: List<Weather>) = dao.saveAll(weathers)

    private fun dtoWeatherToWeather(response: WeatherOneDayResponse, cityName: String) =
            weatherMapper.dtoToPresentation(response, cityName)
}