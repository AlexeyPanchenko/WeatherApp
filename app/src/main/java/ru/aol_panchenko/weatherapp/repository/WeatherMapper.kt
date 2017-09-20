package ru.aol_panchenko.weatherapp.repository

import io.reactivex.Observable
import ru.aol_panchenko.weatherapp.network.model.one_day.WeatherOneDayResponse
import ru.aol_panchenko.weatherapp.presentation.model.Weather

/**
 * Created by alexey on 20.09.17.
 */
class WeatherMapper {

    fun dtoToPresentation(response: WeatherOneDayResponse, cityName:String): Observable<Weather> {
        return Observable.create<Weather> {
           try {
               val weather = Weather.WeatherBuilder()
                       .setCity(cityName)
                       .setType(response.weather?.type)
                       .setDescription(response.weather?.description)
                       .setIcon(response.weather?.icon)
                       .setTemp(response.main?.temp)
                       .setMinTemp(response.main?.minTemp)
                       .setMaxTemp(response.main?.maxTemp)
                       .setPressure(response.main?.pressure)
                       .setHumidity(response.main?.humidity)
                       .setWindSpeed(response.wind?.speed)
                       .setWindDirection(response.wind?.direction)
                       .setCloudiness(response.clouds?.cloudiness)
                       .setRain(response.rain?.amountInThreeHours)
                       .setSnow(response.snow?.amountInThreeHours)
                       .build()
               it.onNext(weather)
               it.onComplete()
           } catch (e: Exception) {
               it.onError(e)
           }
        }
    }
}