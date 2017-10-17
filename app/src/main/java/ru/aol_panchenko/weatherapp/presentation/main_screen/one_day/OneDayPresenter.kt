package ru.aol_panchenko.weatherapp.presentation.main_screen.one_day

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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
        if (_viewModel.weathers.isEmpty()) {
            unsubscribe(_loadWeatherDisposable)
            _loadWeatherDisposable = _repository.getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ fillWeathersList(it); loadWeathers(false) }, {_mvpView.showErrorState(it)})

        } else {
            _mvpView.setWeatherList(_viewModel.weathers)
            initEmptyOrContentState()
        }
    }

    fun onCityNameEntered(cityName: String) {
        loadCityWeather(cityName)
    }

    fun onBackPress() {
        loadWeathers(false)
    }

    fun onRefresh() {
        loadWeathers(false)
    }

    fun onRetryClick() {
        loadWeathers(true)
    }

    fun onDestroy() {
        unsubscribe(_loadWeatherDisposable)
    }

    private fun loadCityWeather(cityName: String) {
        unsubscribe(_loadWeatherDisposable)
        _loadWeatherDisposable = _repository.getWeatherOneDayByCityName(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _mvpView.showProgressState() }
                .subscribe(this::onWeatherLoaded, { _mvpView.showErrorState(it) })
    }

    private fun onWeatherLoaded(weather: Weather) {
        addOrUpdateWeather(weather)
        _viewModel.cityName.value = null
        initEmptyOrContentState()
    }

    private fun loadWeathers(isProgress: Boolean) {
        unsubscribe(_loadWeatherDisposable)
        _loadWeatherDisposable = _repository.loadWeathers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { if (isProgress) _mvpView.showProgressState() }
                .subscribe({ fillWeathersList(it) }, {_mvpView.showErrorState(it)})
    }

    private fun fillWeathersList(weathers: List<Weather>) {
        _viewModel.weathers = weathers as ArrayList<Weather>
        _mvpView.clearList()
        _mvpView.setWeatherList(_viewModel.weathers)
        initEmptyOrContentState()
    }

    private fun addOrUpdateWeather(weather: Weather) {
        var existedWeather: Weather? = null
        var index = 0
        _viewModel.weathers
                .forEachIndexed { i, w ->
                    if (w.cityName == weather.cityName) {
                        existedWeather = w
                        index = i
                        return@forEachIndexed
                    }
                }
        if (existedWeather == null) {
            _viewModel.weathers.add(weather)
            _mvpView.addWeather(weather)
        } else {
            _viewModel.weathers[index] = existedWeather!!
            _mvpView.updateWeather(weather, index)
        }
    }

    private fun initEmptyOrContentState() {
        if (_viewModel.weathers.isEmpty()) {
            _mvpView.showEmptyState()
        } else {
            _mvpView.showContentState()
        }
    }
}