package ru.aol_panchenko.weatherapp.network.model.one_day

import com.google.gson.*
import java.lang.reflect.Type

/**
 * Created by Panchenko.AO on 18.09.2017.
 */
class WeatherOneDayResponseTypeAdapter : JsonDeserializer<WeatherOneDayResponse> {

    private val JSON_COORD = "coord"
    private val JSON_WEATHER = "weather"
    private val JSON_MAIN = "main"
    private val JSON_WIND = "wind"
    private val JSON_CLOUDS = "clouds"
    private val JSON_RAIN = "rain"
    private val JSON_CODE = "cod"

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): WeatherOneDayResponse {
        val responseObj = json?.asJsonObject

        val coord = extractObjectFromJsonObject<CoordResponse>(responseObj?.getAsJsonObject(JSON_COORD))
        val weather = extractObjectFromJsonObject<WeatherResponse>(responseObj?.getAsJsonArray(JSON_WEATHER)!![0])
        val main = extractObjectFromJsonObject<MainResponse>(responseObj.getAsJsonObject(JSON_MAIN))
        val wind = extractObjectFromJsonObject<WindResponse>(responseObj.getAsJsonObject(JSON_WIND))
        val clouds = extractObjectFromJsonObject<CloudResponse>(responseObj.getAsJsonObject(JSON_CLOUDS))
        val rain = extractObjectFromJsonObject<RainResponse>(responseObj.getAsJsonObject(JSON_RAIN))
        val code = responseObj.getAsJsonPrimitive(JSON_CODE).asInt

        return WeatherOneDayResponse(coord, weather, main, wind, clouds, rain, code)
    }

    private inline fun <reified T> extractObjectFromJsonObject(json: JsonElement?) = Gson().fromJson(json, T::class.java)

    companion object {
        fun register(builder: GsonBuilder): GsonBuilder =
                builder.registerTypeAdapter(WeatherOneDayResponse::class.java, WeatherOneDayResponseTypeAdapter())
    }
}