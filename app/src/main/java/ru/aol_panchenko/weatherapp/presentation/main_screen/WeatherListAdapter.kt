package ru.aol_panchenko.weatherapp.presentation.main_screen

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ru.aol_panchenko.weatherapp.R
import ru.aol_panchenko.weatherapp.presentation.model.Weather

/**
 * Created by Panchenko.AO on 19.09.2017.
 */
class WeatherListAdapter(private val _context: Context) : RecyclerView.Adapter<WeatherVH>() {

    private var _weatherList: List<Weather> = ArrayList()

    fun setItems(items: List<Weather>) {
        _weatherList = items
        notifyDataSetChanged()
    }

    fun addItem(item: Weather) {
        val position = itemCount
        (_weatherList as ArrayList).add(item)
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WeatherVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.weather_list_item, null)
        return WeatherVH(view)
    }

    override fun onBindViewHolder(holder: WeatherVH?, position: Int) {
        val weather = _weatherList[position]
        holder?.city?.text = weather.cityName
        holder?.description?.text = weather.description
        holder?.readings?.text = "${weather.temp}°C/${weather.humidity}%"
        Glide.with(_context).load("http://openweathermap.org/img/w/${weather.icon}.png").into(holder?.icon)
    }

    override fun getItemCount() = _weatherList.size
}