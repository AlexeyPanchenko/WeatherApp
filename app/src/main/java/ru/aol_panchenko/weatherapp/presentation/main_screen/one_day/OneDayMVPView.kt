package ru.aol_panchenko.weatherapp.presentation.main_screen.one_day

import ru.aol_panchenko.weatherapp.presentation.model.Weather

/**
 * Created by Panchenko.AO on 19.09.2017.
 */
interface OneDayMVPView {
    fun showErrorState(it: Throwable)
    fun addWeather(response: Weather)
    fun showProgressState()
    fun showContentState()
    fun showEmptyState()
    fun setWeatherList(weathers: List<Weather>)
    fun clearList()
    fun updateWeather(weather: Weather, position: Int)
}