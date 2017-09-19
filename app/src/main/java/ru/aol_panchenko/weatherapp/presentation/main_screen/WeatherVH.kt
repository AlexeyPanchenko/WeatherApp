package ru.aol_panchenko.weatherapp.presentation.main_screen

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.aol_panchenko.weatherapp.R

/**
 * Created by Panchenko.AO on 19.09.2017.
 */
class WeatherVH(private val _itemView: View) : RecyclerView.ViewHolder(_itemView) {

    val city = itemView.findViewById<TextView>(R.id.tvCity)
    val icon = itemView.findViewById<ImageView>(R.id.ivIcon)
    val description = itemView.findViewById<TextView>(R.id.tvDescription)
    val readings = itemView.findViewById<TextView>(R.id.tvReadings)
}