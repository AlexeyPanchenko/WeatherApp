package ru.aol_panchenko.weatherapp.network.model.one_day

import com.google.gson.annotations.SerializedName

/**
 * Created by alexey on 18.09.17.
 */
class WeatherResponse(@SerializedName("main") val type: String?, val description: String?, val icon: String?)