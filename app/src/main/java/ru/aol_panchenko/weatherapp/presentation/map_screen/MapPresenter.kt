package ru.aol_panchenko.weatherapp.presentation.map_screen

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.aol_panchenko.weatherapp.LOCATION_PERMISSION
import ru.aol_panchenko.weatherapp.LOCATION_PERMISSION_REQUEST_CODE
import ru.aol_panchenko.weatherapp.UNKNOWN_TERRITORY
import ru.aol_panchenko.weatherapp.WeatherApplication
import ru.aol_panchenko.weatherapp.repository.OneDayRepository
import ru.aol_panchenko.weatherapp.utils.unsubscribe
import java.util.*
import javax.inject.Inject

/**
 * Created by Panchenko.AO on 18.10.2017.
 */
class MapPresenter(private val _mvpView: MapMVPView) {

    @Inject lateinit var _context: WeatherApplication
    @Inject lateinit var _repository: OneDayRepository
    private var _loadWeatherDisposable: Disposable? = null
    private var _geocoder: Geocoder? = null
    private var _latitude: Double? = null
    private var _longitude: Double? = null

    init {
        WeatherApplication.appComponent.inject(this)
        _geocoder = Geocoder(_context, Locale.getDefault())
    }

    fun onMapReady() {
        if (_mvpView.hasPermission()) {
            _mvpView.enableLocation()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _mvpView.createRequest(arrayOf(LOCATION_PERMISSION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    fun onMarkerClick(marker: Marker?) {
        _latitude = marker?.position?.latitude
        _longitude = marker?.position?.longitude
        Single.create<Address> { e ->
            val addresses = _geocoder?.getFromLocation(_latitude!!, _longitude!!, 1)
            val address = if (addresses!!.isNotEmpty()) addresses[0] else null
            e.onSuccess(address) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _mvpView.choosePlaceDialog(extractLocationName(it)) },
                        { _mvpView.choosePlaceDialog(UNKNOWN_TERRITORY) })
    }

    fun onMapClick(latLon: LatLng?) {
        _mvpView.setMarkerInPoint(latLon, 0F, 1000)
    }

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        var allowed = true
        when(requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> { grantResults.forEach { allowed = allowed && (it == PackageManager.PERMISSION_GRANTED) }}
            else -> allowed = false
        }
        if (allowed) {
            _mvpView.enableLocation()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                _mvpView.notifyDenied(LOCATION_PERMISSION)
            }
        }
    }

    fun onActivityResult() {
        if (_mvpView.hasPermission()) {
            _mvpView.enableLocation()
        }
    }

    fun onLocationButtonClick() {
        if (_mvpView.gpsEnable()) {
            _mvpView.showLocation()
        } else {
            _mvpView.showTurnOnGeoDialog()
        }
    }

    fun onLocationConnected() {
        val locReq = LocationRequest.create()
        locReq.priority = LocationRequest.PRIORITY_LOW_POWER
        _mvpView.requestLoadLocation(locReq)
    }

    fun onLocationLoaded(location: Location) {
        val latLon = LatLng(location.latitude, location.longitude)
        _mvpView.setMarkerInPoint(latLon, 15F, 2500)
    }

    fun onShouldNotRequestPermissionRationale() {
        _mvpView.showLocationNotAllowed()
    }

    fun onShouldRequestPermissionRationale() {
        _mvpView.showNeedSettingsDialog()
    }

    fun onForwardSettingsOkClick() {
        _mvpView.openSettings()
    }

    fun onTurnGeoOkClick() {
        _mvpView.changeGeo()
    }

    fun onChoosePlace() {
        loadWeather()
    }

    fun onStop() {
        _mvpView.disconnectClient()
    }

    fun onDestroy() {
        unsubscribe(_loadWeatherDisposable)
    }

    private fun extractLocationName(address: Address): String {
        val county = if (!address.countryName.isNullOrEmpty()) address.countryName else ""
        val adminArea = if (!address.adminArea.isNullOrEmpty()) address.adminArea else ""
        val subAdminArea = if (!address.subAdminArea.isNullOrEmpty()) address.subAdminArea else ""
        return "$county $adminArea $subAdminArea"
    }

    private fun loadWeather() {
        unsubscribe(_loadWeatherDisposable)
        _loadWeatherDisposable = _repository.getWeatherOneDayByGeo(_latitude!!, _longitude!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _mvpView.closeMap() }, { _mvpView.showError() })
    }
}