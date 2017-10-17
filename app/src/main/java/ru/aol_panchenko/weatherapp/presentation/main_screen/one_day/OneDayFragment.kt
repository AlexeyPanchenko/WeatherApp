package ru.aol_panchenko.weatherapp.presentation.main_screen.one_day

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.error_state.*
import kotlinx.android.synthetic.main.weather_list_fragment.*
import org.jetbrains.anko.support.v4.onRefresh
import ru.aol_panchenko.weatherapp.R
import ru.aol_panchenko.weatherapp.presentation.add_city.AddCityViewModel
import ru.aol_panchenko.weatherapp.presentation.main_screen.OnBackPressListener
import ru.aol_panchenko.weatherapp.presentation.main_screen.WeatherListAdapter
import ru.aol_panchenko.weatherapp.presentation.model.Weather
import ru.aol_panchenko.weatherapp.utils.ui.SpacesItemDecoration

/**
 * Created by alexey on 19.09.17.
 */
class OneDayFragment : Fragment(), OneDayMVPView, LifecycleRegistryOwner, OnBackPressListener {

    private val _registry = LifecycleRegistry(this)
    private var _presenter: OneDayPresenter? = null
    private var _adapter: WeatherListAdapter? = null
    private var _viewModel: AddCityViewModel? = null

    companion object {
        fun newInstance() = OneDayFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.weather_list_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        _viewModel = ViewModelProviders.of(activity).get(AddCityViewModel::class.java)
        _presenter = OneDayPresenter(this, _viewModel!!)
        btnRetry.setOnClickListener({ _presenter?.onRetryClick() })
        observeViewModel()
        initSwipeLayout()
    }

    override fun showErrorState(it: Throwable) {
        changeState(View.GONE, errorVisibility = View.VISIBLE)
        swipeLayout?.isRefreshing = false
    }

    override fun addWeather(weather: Weather) {
        _adapter?.addItem(weather)
    }

    override fun updateWeather(weather: Weather, position: Int) {
        _adapter?.updateItem(weather, position)
    }

    override fun setWeatherList(weathers: List<Weather>) {
        _adapter?.setItems(weathers)
        swipeLayout?.isRefreshing = false
    }

    override fun showProgressState() {
        changeState(View.GONE, View.VISIBLE)
    }

    override fun showContentState() {
        changeState()
    }

    override fun showEmptyState() {
        changeState(View.GONE, emptyVisibility = View.VISIBLE)
    }

    override fun clearList() {
        _adapter?.clearList()
    }

    private fun initSwipeLayout() {
        swipeLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
        swipeLayout.onRefresh { _presenter?.onRefresh() }
    }

    private fun initRecyclerView() {
        _adapter = WeatherListAdapter(activity)
        rvWeatherList.addItemDecoration(SpacesItemDecoration(20))
        rvWeatherList.adapter = _adapter
        rvWeatherList.layoutManager = LinearLayoutManager(activity)
    }

    private fun observeViewModel() {
        _viewModel?.cityName?.observe(this, Observer {
            if (it != null) {
                if (it.isNotEmpty()) {
                    _presenter?.onCityNameEntered(it)
                }
            }
        })
    }

    private fun changeState(rvVisibility: Int = View.VISIBLE, progressVisibility: Int = View.GONE, errorVisibility: Int = View.GONE, emptyVisibility: Int = View.GONE) {
        rvWeatherList?.visibility = rvVisibility
        progressContainer?.visibility = progressVisibility
        errorContainer?.visibility = errorVisibility
        emptyContainer?.visibility = emptyVisibility
    }

    override fun getLifecycle() = _registry

    override fun onBackPress() {
        if (errorContainer?.visibility == View.VISIBLE) {
            _presenter?.onBackPress()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _presenter?.onDestroy()
        _presenter.let { _presenter = null }
    }
}