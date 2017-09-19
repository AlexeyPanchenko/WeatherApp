package ru.aol_panchenko.weatherapp.network.model.one_day

/**
 * Created by Panchenko.AO on 18.09.2017.
 */
class WeatherOneDayResponse(val coord: CoordResponse?, val weather: WeatherResponse?, val main: MainResponse?,
                            val wind: WindResponse?, val clouds: CloudResponse?, val rain: RainResponse?,
                            val snow: SnowResponse?, val code: Int)