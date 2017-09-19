package ru.aol_panchenko.weatherapp.network.model.one_day

import com.google.gson.annotations.SerializedName

/**
 * Created by alexey on 20.09.17.
 */
class SnowResponse(@SerializedName("3h") val amountInThreeHours: Double)