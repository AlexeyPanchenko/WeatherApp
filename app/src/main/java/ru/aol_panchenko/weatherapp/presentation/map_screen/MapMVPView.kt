package ru.aol_panchenko.weatherapp.presentation.map_screen

import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Panchenko.AO on 18.10.2017.
 */
interface MapMVPView {
    fun hasPermission(): Boolean
    fun enableLocation()
    fun createRequest(permissions: Array<String>, requestCode: Int)
    fun disconnectClient()
    fun choosePlaceDialog(locationName: String)
    fun setMarkerInPoint(latLon: LatLng?, zoom: Float, duration: Int)
    fun gpsEnable(): Boolean
    fun showLocation()
    fun showTurnOnGeoDialog()
    fun requestLoadLocation(locReq: LocationRequest?)
    fun notifyDenied(permission: String)
    fun showNeedSettingsDialog()
    fun showLocationNotAllowed()
    fun openSettings()
    fun changeGeo()
    fun closeMap()
    fun showError()
}