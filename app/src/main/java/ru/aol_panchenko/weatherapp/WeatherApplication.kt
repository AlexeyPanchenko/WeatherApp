package ru.aol_panchenko.weatherapp

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.aol_panchenko.weatherapp.di.*

/**
 * Created by alexey on 17.09.17.
 */

class WeatherApplication : Application() {

    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        /*Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(realmConfiguration)*/

        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .mapperModule(MapperModule())
                .daoModule(DaoModule())
                .build()
    }
}
