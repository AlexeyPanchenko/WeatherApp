package ru.aol_panchenko.weatherapp.presentation.main_screen

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ru.aol_panchenko.weatherapp.presentation.main_screen.one_day.OneDayFragment

/**
 * Created by alexey on 19.09.17.
 */
class WeatherPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    val ONE_DAY_FRAGMENT = 0
    val FIVE_DAYS_FRAGMENT = 1
    val SIXTEEN_DAYS_FRAGMENT = 2

    override fun getItem(position: Int) = when(position) {
        ONE_DAY_FRAGMENT -> OneDayFragment.newInstance()
        FIVE_DAYS_FRAGMENT -> OneDayFragment.newInstance()
        SIXTEEN_DAYS_FRAGMENT -> OneDayFragment.newInstance()
        else -> throw IllegalStateException("impossible")
    }

    override fun getCount() = 3
}