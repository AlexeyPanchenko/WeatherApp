package ru.aol_panchenko.weatherapp.network.model.one_day

/**
 * Created by Panchenko.AO on 18.09.2017.
 */
class WeatherOneDayResponse(coord: CoordResponse, weather: WeatherResponse, main: MainReaponse,
                            wind: WindResponse, clouds: CloudResponse, rain: RainResponse, val code: Int)