package ru.aol_panchenko.weatherapp.presentation.add_city

/**
 * Created by alexey on 17.09.17.
 */
class AddCityPresenter(private val _mvpView: AddCityMVPView, private val _viewModel: AddCityViewModel) {

    fun onSaveClick() {
        _viewModel.cityName.value = _mvpView.getCityName().trim()
        _mvpView.closeDialog()
    }

    fun onCancelClick() {
        _mvpView.closeDialog()
    }
}