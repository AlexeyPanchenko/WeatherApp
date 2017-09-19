package ru.aol_panchenko.weatherapp.presentation

/**
 * Created by alexey on 20.09.17.
 */
class Weather private constructor(val type: String, val description: String,
                          val icon: String, val temp: Double,
                          val minTemp: Double, val maxTemp: Double,
                          val preassure: Int, val humidity: Int,
                          val windSpeed: Double,  val windDirection: Double,
                          val cloudiness: Int, val rainInThreeHours: Double,
                          val snowInThreeHours: Double) {

    class WeatherBuilder {
        private var type: String = ""
        private var description: String = ""
        private var icon: String = ""
        private var temp: Double = 0.0
        private var minTemp: Double = 0.0
        private var maxTemp: Double = 0.0
        private var preassure: Int = 0
        private var humidity: Int = 0
        private var windSpeed: Double = 0.0
        private var windDirection: Double = 0.0
        private var cloudiness: Int = 0
        private var rainInThreeHours: Double = 0.0
        private var snowInThreeHours: Double = 0.0

        fun setType(type: String): WeatherBuilder {
            this.type = type
            return this
        }

        fun setDescription(description: String): WeatherBuilder {
            this.description = description
            return this
        }

        fun setIcon(icon: String): WeatherBuilder {
            this.icon = icon
            return this
        }

        fun setTemp(temp: Double): WeatherBuilder {
            this.temp = temp
            return this
        }

        fun setMinTemp(minTemp: Double): WeatherBuilder {
            this.minTemp = minTemp
            return this
        }

        fun setMaxTemp(maxTemp: Double): WeatherBuilder {
            this.maxTemp = maxTemp
            return this
        }

        fun setPreassure(preassure: Int): WeatherBuilder {
            this.preassure = preassure
            return this
        }

        fun setHumidity(humidity: Int): WeatherBuilder {
            this.humidity = humidity
            return this
        }

        fun setWindSpeed(windSpeed: Double): WeatherBuilder {
            this.windSpeed = windSpeed
            return this
        }

        fun setWindDirection(windDirection: Double): WeatherBuilder {
            this.windDirection = windDirection
            return this
        }

        fun setCloudiness(cloudiness: Int): WeatherBuilder {
            this.cloudiness = cloudiness
            return this
        }

        fun setRain(rainInThreeHours: Double): WeatherBuilder {
            this.rainInThreeHours = rainInThreeHours
            return this
        }

        fun setSnow(snowInThreeHours: Double): WeatherBuilder {
            this.snowInThreeHours = snowInThreeHours
            return this
        }

        fun build(): Weather {
            return Weather(type, description, icon, temp, minTemp, maxTemp, preassure,
                    humidity, windSpeed, windDirection, cloudiness, rainInThreeHours, snowInThreeHours)
        }
    }

}