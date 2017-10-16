package ru.aol_panchenko.weatherapp.presentation.main_screen.one_day

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toFlowable
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
        if (_viewModel.weathers.isEmpty()) {
            unsubscribe(_loadWeatherDisposable)
            _loadWeatherDisposable = _repository.getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { _mvpView.showProgressState() }
                    .subscribe({
                        _viewModel.weathers = it as ArrayList<Weather>
                        _mvpView.clearList()
                        _mvpView.setWeatherList(_viewModel.weathers)
                        initEmptyOrContentState()})
        } else {
            _mvpView.setWeatherList(_viewModel.weathers)
            initEmptyOrContentState()
        }
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
                .subscribe(this::onWeatherLoaded, { _mvpView.showErrorState(it) })
    }

    fun onRefresh(isSwipe: Boolean) {
        when {
            _viewModel.weathers.isNotEmpty() -> {
                _mvpView.clearList()
                val cities = arrayListOf<String>()
                _viewModel.weathers.forEach { it.cityName?.let { it1 -> cities.add(it1) } }
                if (_viewModel.cityIsNotEmpty() && !cities.contains(_viewModel.cityName.value)) {
                    cities.add(_viewModel.cityName.value!!)
                }
                cities.toFlowable()
                        //.map { it.cityName!! }
                        .flatMap { _repository.getWeatherOneDayByCityName(it) }
                        .subscribeOn(Schedulers.io())
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { if (!isSwipe) _mvpView.showProgressState() }
                        .doOnSuccess { _viewModel.reset() }
                        .subscribe({ refreshWeatherLoaded(it) }, { _mvpView.showErrorState(it) })
            }
            _viewModel.cityIsNotEmpty() -> loadCityWeather(_viewModel.cityName.value!!)
            else -> _mvpView.setWeatherList(_viewModel.weathers)
        }
    }

    private fun onWeatherLoaded(weather: Weather) {
        addOrUpdateWeather(weather)
        _viewModel.cityName.value = null
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

    private fun refreshWeatherLoaded(weathers: List<Weather>) {
        _viewModel.weathers.addAll(weathers)
        initEmptyOrContentState()
        _mvpView.setWeatherList(weathers)
    }

    private fun resetViewModel() {
        _viewModel.reset()
    }

    fun onDestroy() {
        unsubscribe(_loadWeatherDisposable)
    }

    fun onRetryClick() {
        onRefresh(false)
    }
}