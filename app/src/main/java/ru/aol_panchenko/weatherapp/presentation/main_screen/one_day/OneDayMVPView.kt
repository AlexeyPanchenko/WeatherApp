package ru.aol_panchenko.weatherapp.presentation.main_screen.one_day

import ru.aol_panchenko.weatherapp.network.model.one_day.WeatherOneDayResponse

/**
 * Created by Panchenko.AO on 19.09.2017.
 */
interface OneDayMVPView {
    fun showErrorState(it: Throwable)
    fun addWeather(response: WeatherOneDayResponse)
    fun getApiKey(): String
    fun showProgressState()
    fun showContentState()
}