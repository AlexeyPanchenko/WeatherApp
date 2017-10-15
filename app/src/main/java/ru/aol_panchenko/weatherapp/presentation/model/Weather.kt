package ru.aol_panchenko.weatherapp.presentation.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import io.realm.RealmObject

/**
 * Created by alexey on 20.09.17.
 */
@Entity(tableName = "weathers_one_day")
class Weather(){

    var type: String? = null
    var description: String? = null
    var icon: String? = null
    var temp: Double? = null
    var minTemp: Double? = null
    var maxTemp: Double? = null
    var preassure: Int? = null
    var humidity: Int? = null
    var windSpeed: Double? = null
    var windDirection: Double? = null
    var cloudiness: Int? = null
    var rainInThreeHours: Double? = null
    var snowInThreeHours: Double? = null
    @PrimaryKey
    var cityName: String? = null

    @Ignore
    constructor(type: String?, description: String?,
                        icon: String?, temp: Double?,
                        minTemp: Double?, maxTemp: Double?,
                        preassure: Int?, humidity: Int?,
                        windSpeed: Double?,  windDirection: Double?,
                        cloudiness: Int?, rainInThreeHours: Double?,
                        snowInThreeHours: Double?, cityName: String?) : this() {
        this.description = description
        this.icon = icon
        this.temp = temp
        this.humidity = humidity
        this.cityName = cityName
    }

    class WeatherBuilder {
        private var type: String? = null
        private var description: String? = null
        private var icon: String? = null
        private var temp: Double? = null
        private var minTemp: Double? = null
        private var maxTemp: Double? = null
        private var pressure: Int? = null
        private var humidity: Int? = null
        private var windSpeed: Double? = null
        private var windDirection: Double? = null
        private var cloudiness: Int? = null
        private var rainInThreeHours: Double? = null
        private var snowInThreeHours: Double? = null
        private var city: String? = null

        fun setType(type: String?): WeatherBuilder {
            this.type = type
            return this
        }

        fun setDescription(description: String?): WeatherBuilder {
            this.description = description
            return this
        }

        fun setIcon(icon: String?): WeatherBuilder {
            this.icon = icon
            return this
        }

        fun setTemp(temp: Double?): WeatherBuilder {
            this.temp = temp
            return this
        }

        fun setMinTemp(minTemp: Double?): WeatherBuilder {
            this.minTemp = minTemp
            return this
        }

        fun setMaxTemp(maxTemp: Double?): WeatherBuilder {
            this.maxTemp = maxTemp
            return this
        }

        fun setPressure(pressure: Int?): WeatherBuilder {
            this.pressure = pressure
            return this
        }

        fun setHumidity(humidity: Int?): WeatherBuilder {
            this.humidity = humidity
            return this
        }

        fun setWindSpeed(windSpeed: Double?): WeatherBuilder {
            this.windSpeed = windSpeed
            return this
        }

        fun setWindDirection(windDirection: Double?): WeatherBuilder {
            this.windDirection = windDirection
            return this
        }

        fun setCloudiness(cloudiness: Int?): WeatherBuilder {
            this.cloudiness = cloudiness
            return this
        }

        fun setRain(rainInThreeHours: Double?): WeatherBuilder {
            this.rainInThreeHours = rainInThreeHours
            return this
        }

        fun setSnow(snowInThreeHours: Double?): WeatherBuilder {
            this.snowInThreeHours = snowInThreeHours
            return this
        }

        fun setCity(city: String): WeatherBuilder {
            this.city = city
            return this
        }

        fun build(): Weather {
            return Weather(type, description, icon, temp, minTemp, maxTemp, pressure,
                    humidity, windSpeed, windDirection, cloudiness, rainInThreeHours, snowInThreeHours, city)
        }
    }

}