package ru.aol_panchenko.weatherapp.presentation.main_screen.one_day

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.error_state.*
import kotlinx.android.synthetic.main.weather_list_fragment.*
import ru.aol_panchenko.weatherapp.R
import ru.aol_panchenko.weatherapp.network.model.one_day.WeatherOneDayResponse
import ru.aol_panchenko.weatherapp.presentation.add_city.AddCityViewModel
import ru.aol_panchenko.weatherapp.presentation.main_screen.WeatherListAdapter
import ru.aol_panchenko.weatherapp.utils.ui.SpacesItemDecoration

/**
 * Created by alexey on 19.09.17.
 */
class OneDayFragment : Fragment(), OneDayMVPView, LifecycleRegistryOwner {

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
        val view = inflater?.inflate(R.layout.weather_list_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        _viewModel = ViewModelProviders.of(activity).get(AddCityViewModel::class.java)
        _presenter = OneDayPresenter(this, _viewModel!!)
        btnRetry.setOnClickListener({ _presenter?.onRetryClick() })
        observeViewModel()
    }

    private fun initRecyclerView() {
        _adapter = WeatherListAdapter(activity)
        rvWeatherList.addItemDecoration(SpacesItemDecoration(20))
        rvWeatherList.adapter = _adapter
        rvWeatherList.layoutManager = LinearLayoutManager(activity)
    }

    private fun observeViewModel() {
        _viewModel?.cityName?.observe(this, Observer {
            it.let {
                if (it?.isNotEmpty()!!) {
                    _presenter?.loadCityWeather(it, getApiKey())
                }
            }
        })
    }

    override fun showErrorState(it: Throwable) {
        changeState(errorVisibility = View.VISIBLE)
    }

    override fun addWeather(response: WeatherOneDayResponse) {
        _adapter?.addItem(response)
    }

    override fun showProgressState() {
        changeState(View.VISIBLE)
    }

    override fun showContentState() {
        changeState()
    }

    private fun changeState(progressVisibility: Int = View.GONE, errorVisibility: Int = View.GONE) {
        progressContainer?.visibility = progressVisibility
        errorContainer?.visibility = errorVisibility
    }

    override fun getApiKey(): String = getString(R.string.weather_api_key)

    override fun getLifecycle() = _registry

    override fun onDestroy() {
        super.onDestroy()
        _presenter?.onDestroy()
        _presenter.let { _presenter = null }
    }
}