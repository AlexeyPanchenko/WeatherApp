package ru.aol_panchenko.weatherapp.di

import dagger.Component
import ru.aol_panchenko.weatherapp.dao.WeatherOneDayDao
import ru.aol_panchenko.weatherapp.presentation.main_screen.one_day.OneDayPresenter
import ru.aol_panchenko.weatherapp.repository.OneDayRepository
import javax.inject.Singleton

/**
 * Created by alexey on 18.09.17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, MapperModule::class, DaoModule::class))
interface AppComponent {
    fun inject(target: OneDayRepository)
    fun inject(target: OneDayPresenter)
    fun inject(target: WeatherOneDayDao)
}