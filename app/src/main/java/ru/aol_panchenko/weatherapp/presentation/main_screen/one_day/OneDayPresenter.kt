package ru.aol_panchenko.weatherapp.presentation.main_screen.one_day

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import ru.aol_panchenko.weatherapp.WeatherApplication
import ru.aol_panchenko.weatherapp.presentation.add_city.AddCityViewModel
import ru.aol_panchenko.weatherapp.presentation.model.Weather
import ru.aol_panchenko.weatherapp.repository.OneDayRepository
import ru.aol_panchenko.weatherapp.utils.unsubscribe
import javax.inject.Inject

/**
 * Created by Panchenko.AO on 19.09.2017.
 */
class OneDayPresenter(private val _mvpView: OneDayMVPView, private val _viewModel: AddCityViewModel) {

    @Inject lateinit var _repository: OneDayRepository

    private var _loadWeatherDisposable: Disposable? = null

    init {
        WeatherApplication.appComponent.inject(this)
        if (_viewModel.weathers.isNotEmpty()) {
            _mvpView.setWeatherList(_viewModel.weathers)
        }
        initEmptyOrContentState()
    }

    fun onCityNameEntered(cityName: String) {
        loadCityWeather(cityName)
    }

    private fun loadCityWeather(cityName: String) {
        unsubscribe(_loadWeatherDisposable)
        _loadWeatherDisposable = _repository.getWeatherOneDayByCityName(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _mvpView.showProgressState() }
                .subscribe(this::onWeatherLoaded, {_mvpView.showErrorState(it) })
    }

    private fun onWeatherLoaded(weather: Weather) {
        _viewModel.cityName.value = null
        _viewModel.weathers.add(weather)
        initEmptyOrContentState()
        _mvpView.addWeather(weather)
    }

    private fun initEmptyOrContentState() {
        if (_viewModel.weathers.isEmpty()) {
            _mvpView.showEmptyState()
        } else {
            _mvpView.showContentState()
        }
    }

    fun onRefresh() {
        if (_viewModel.weathers.isNotEmpty()) {
            _mvpView.clearList()
            _viewModel.weathers.toObservable()
                    .map { it.cityName!! }
                    .flatMap { _repository.getWeatherOneDayByCityName(it) }
                    .subscribeOn(Schedulers.io())
                    .toList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { reset(it) }
                    .subscribe({ refreshWeatherLoaded(it) }, { _mvpView.showErrorState(it) })
        } else _mvpView.setWeatherList(_viewModel.weathers)
    }

    private fun refreshWeatherLoaded(it: List<Weather>) {
        initEmptyOrContentState()
        _mvpView.setWeatherList(it)
    }

    private fun reset(weathers: List<Weather>) {
        _viewModel.reset()
        _viewModel.weathers.addAll(weathers)
    }

    fun onDestroy() {
        unsubscribe(_loadWeatherDisposable)
    }

    fun onRetryClick() {
        onRefresh()
    }
}