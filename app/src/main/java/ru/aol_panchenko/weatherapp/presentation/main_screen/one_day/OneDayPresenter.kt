package ru.aol_panchenko.weatherapp.presentation.main_screen.one_day

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.aol_panchenko.weatherapp.WeatherApplication
import ru.aol_panchenko.weatherapp.network.model.one_day.WeatherOneDayResponse
import ru.aol_panchenko.weatherapp.repository.OneDayRepository
import ru.aol_panchenko.weatherapp.utils.unsubscribe
import javax.inject.Inject

/**
 * Created by Panchenko.AO on 19.09.2017.
 */
class OneDayPresenter(private val _mvpView: OneDayMVPView) {

    @Inject lateinit var _repository: OneDayRepository

    private var _loadWeatherDisposable: Disposable? = null

    init {
        WeatherApplication.appComponent.inject(this)
        loadWeather("Владивосток", _mvpView.getApiKey())
    }

    private fun loadWeather(cityName: String, apiKey: String) {
        unsubscribe(_loadWeatherDisposable)
        _loadWeatherDisposable = _repository.getWeatherOneDayByCityName(cityName, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _mvpView.showProgressState() }
                .subscribe(this::onWeatherLoaded, {_mvpView.showErrorState(it) })
    }

    private fun onWeatherLoaded(response: WeatherOneDayResponse) {
        _mvpView.showContentState()
        _mvpView.addWeather(response)
    }

    fun onDestroy() {
        unsubscribe(_loadWeatherDisposable)
    }

    fun onRetryClick() {

    }
}