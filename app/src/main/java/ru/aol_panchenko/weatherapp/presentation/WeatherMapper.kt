package ru.aol_panchenko.weatherapp.presentation

import android.content.Context
import io.reactivex.Single
import ru.aol_panchenko.weatherapp.R
import ru.aol_panchenko.weatherapp.network.model.one_day.WeatherOneDayResponse

/**
 * Created by alexey on 20.09.17.
 */
class WeatherMapper(private val _context: Context) {

    fun dtoToPresentation(response: WeatherOneDayResponse) {
        Single.create<Weather> {
            val description = when(response.weather?.id) {
                200 -> _context.getString(R.string.weather_description_200)
                201 -> _context.getString(R.string.weather_description_201)
                202 -> _context.getString(R.string.weather_description_202)
                210 -> _context.getString(R.string.weather_description_210)
                211 -> _context.getString(R.string.weather_description_211)
                212 -> _context.getString(R.string.weather_description_212)
                221 -> _context.getString(R.string.weather_description_221)
                230 -> _context.getString(R.string.weather_description_230)
                231 -> _context.getString(R.string.weather_description_231)
                232 -> _context.getString(R.string.weather_description_232)
                else -> throw IllegalStateException("Incorrect weather description")
            }
        }
    }
}