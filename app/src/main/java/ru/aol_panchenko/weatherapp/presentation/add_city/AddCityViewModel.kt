package ru.aol_panchenko.weatherapp.presentation.add_city

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.aol_panchenko.weatherapp.presentation.model.Weather

/**
 * Created by alexey on 20.09.17.
 */
class AddCityViewModel : ViewModel() {

    var cityName = MutableLiveData<String?>()
    var weathers = ArrayList<Weather>(0)

    fun reset() {
        cityName.value = null
        weathers.clear()
    }

    fun cityIsNotEmpty() = cityName.value != null && cityName.value?.isNotEmpty()!!
}