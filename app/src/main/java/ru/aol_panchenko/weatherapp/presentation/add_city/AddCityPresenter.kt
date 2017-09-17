package ru.aol_panchenko.weatherapp.presentation.add_city

import android.util.Log

/**
 * Created by alexey on 17.09.17.
 */
class AddCityPresenter(private val _mvpView: AddCityMVPView) {

    fun onSaveClick() {
        val cityName = _mvpView.getCityName()
        Log.d("TTT", cityName)
        _mvpView.closeDialog()
    }

    fun onCancelClick() {
        _mvpView.closeDialog()
    }
}