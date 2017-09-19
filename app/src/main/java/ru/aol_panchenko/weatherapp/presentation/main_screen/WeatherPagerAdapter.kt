package ru.aol_panchenko.weatherapp.presentation.main_screen

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ru.aol_panchenko.weatherapp.R
import ru.aol_panchenko.weatherapp.presentation.main_screen.five_days.FiveDaysFragment
import ru.aol_panchenko.weatherapp.presentation.main_screen.one_day.OneDayFragment
import ru.aol_panchenko.weatherapp.presentation.main_screen.sixteen_days.SixteenDaysFragment

/**
 * Created by alexey on 19.09.17.
 */
class WeatherPagerAdapter(fragmentManager: FragmentManager, private val _context: Context) : FragmentPagerAdapter(fragmentManager) {

    val ONE_DAY_FRAGMENT = 0
    val FIVE_DAYS_FRAGMENT = 1
    val SIXTEEN_DAYS_FRAGMENT = 2

    override fun getItem(position: Int) = when(position) {
        ONE_DAY_FRAGMENT -> OneDayFragment.newInstance()
        FIVE_DAYS_FRAGMENT -> FiveDaysFragment.newInstance()
        SIXTEEN_DAYS_FRAGMENT -> SixteenDaysFragment.newInstance()
        else -> throw IllegalStateException("impossible")
    }

    override fun getPageTitle(position: Int) = when(position) {
        ONE_DAY_FRAGMENT -> _context.getString(R.string.today_title)
        FIVE_DAYS_FRAGMENT -> _context.getString(R.string.fie_days_title)
        SIXTEEN_DAYS_FRAGMENT -> _context.getString(R.string.sixteen_days_title)
        else -> throw IllegalStateException("impossible")
    }

    override fun getCount() = 3
}