package ru.aol_panchenko.weatherapp.di

import dagger.Component
import ru.aol_panchenko.weatherapp.presentation.MainActivity
import javax.inject.Singleton

/**
 * Created by alexey on 18.09.17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(target: MainActivity)
}