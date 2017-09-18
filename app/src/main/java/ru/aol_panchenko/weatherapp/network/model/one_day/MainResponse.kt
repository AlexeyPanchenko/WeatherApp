package ru.aol_panchenko.weatherapp.network.model.one_day

import com.google.gson.annotations.SerializedName

/**
 * Created by Panchenko.AO on 18.09.2017.
 */
class MainResponse(val temp: Double, val pressure: Int, val humidity: Int,
                   @SerializedName("temp_min") val minTemp: Double, @SerializedName("temp_max") val maxTemp: Double)