package ru.aol_panchenko.weatherapp.presentation.model

/**
 * Created by alexey on 20.09.17.
 */
class Weather private constructor(val type: String?, val description: String?,
                          val icon: String?, val temp: Double?,
                          val minTemp: Double?, val maxTemp: Double?,
                          val preassure: Int?, val humidity: Int?,
                          val windSpeed: Double?,  val windDirection: Double?,
                          val cloudiness: Int?, val rainInThreeHours: Double?,
                          val snowInThreeHours: Double?, val cityName: String?) {

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