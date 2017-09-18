package ru.aol_panchenko.weatherapp.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.aol_panchenko.weatherapp.BASE_URL
import ru.aol_panchenko.weatherapp.BuildConfig
import ru.aol_panchenko.weatherapp.HTTP_TIMEOUT_MILLIS
import ru.aol_panchenko.weatherapp.network.model.one_day.WeatherOneDayResponseTypeAdapter
import java.util.concurrent.TimeUnit

/**
 * Created by Panchenko.AO on 18.09.2017.
 */
class RetrofitBuilder {

    companion object {
        fun create(): ApiWeather {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(ApiWeather::class.java)
        }

        private fun getGson(): Gson? {
            var gsonBuilder = GsonBuilder()
            gsonBuilder = WeatherOneDayResponseTypeAdapter.register(gsonBuilder)
            return gsonBuilder.create()
        }

        private fun getClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                    .readTimeout(HTTP_TIMEOUT_MILLIS.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(HTTP_TIMEOUT_MILLIS.toLong(), TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }

            return builder.build()
        }
    }
}