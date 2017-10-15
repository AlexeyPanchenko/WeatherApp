package ru.aol_panchenko.weatherapp.dao

import android.content.Context
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.Observable
import io.realm.Realm
import ru.aol_panchenko.weatherapp.WeatherApplication
import ru.aol_panchenko.weatherapp.presentation.model.Weather
import javax.inject.Inject
import io.realm.RealmConfiguration



/**
 * Created by alexey on 15.10.17.
 */
class WeatherOneDayDao @Inject constructor(val realm: Realm) {

   /* fun save(weather: Weather) {
        realm.executeTransactionAsync { it.copyToRealmOrUpdate(weather) }
    }

    fun saveAll(weathers: List<Weather>) {
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(weathers)
        realm.commitTransaction()
    }

    fun getAll() = Flowable.create<List<Weather>>({ e ->
        try {
            e.onNext(realm.where(Weather::class.java).findAllAsync())
            e.onComplete()
        }catch (ex: Exception) {
            e.onError(ex)
        }}, BackpressureStrategy.BUFFER)


        fun findByCityName(cityName: String) =
                realm.where(Weather::class.java).equalTo("cityName", cityName).findFirst()*/
}