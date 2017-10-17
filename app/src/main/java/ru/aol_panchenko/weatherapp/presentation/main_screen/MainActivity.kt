package ru.aol_panchenko.weatherapp.presentation.main_screen

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.aol_panchenko.weatherapp.R
import ru.aol_panchenko.weatherapp.WeatherApplication
import ru.aol_panchenko.weatherapp.network.ApiWeather
import ru.aol_panchenko.weatherapp.network.model.one_day.WeatherOneDayResponse
import ru.aol_panchenko.weatherapp.presentation.add_city.AddCityDialog
import ru.aol_panchenko.weatherapp.presentation.main_screen.one_day.OneDayFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _pageAdapter: WeatherPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = ""

        _pageAdapter = WeatherPagerAdapter(supportFragmentManager, this)
        vpContainerFragment.adapter = _pageAdapter
        tlWeatherVariety.setupWithViewPager(vpContainerFragment)

        fabAddCity.setOnClickListener { view ->
            AddCityDialog.show(supportFragmentManager)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        var listener: OnBackPressListener? = null
        supportFragmentManager.fragments.forEach { if (it is OnBackPressListener) listener = it }
        if (listener != null)  {
            listener?.onBackPress()
        } else {
            super.onBackPressed()
        }

    }
}
