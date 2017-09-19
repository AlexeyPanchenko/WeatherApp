package ru.aol_panchenko.weatherapp.presentation.add_city

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by alexey on 20.09.17.
 */
class AddCityViewModel : ViewModel() {

    var cityName = MutableLiveData<String>()
}