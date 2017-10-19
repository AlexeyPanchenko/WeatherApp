package ru.aol_panchenko.weatherapp.presentation.map_screen

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import ru.aol_panchenko.weatherapp.LOCATION_PERMISSION_REQUEST_CODE
import ru.aol_panchenko.weatherapp.LOCATION_PERMISSION
import ru.aol_panchenko.weatherapp.R
import ru.aol_panchenko.weatherapp.presentation.main_screen.MainActivity

/**
 * Created by Panchenko.AO on 18.10.2017.
 */
class MapActivity : AppCompatActivity(), MapMVPView,
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private var _presenter: MapPresenter? = null
    private var _googleMap: GoogleMap? = null
    private var _marker: Marker? = null
    private var _mapClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maps_activity)
        _presenter = MapPresenter(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        initGoogleMap(googleMap)
        _presenter?.onMapReady()
    }

    override fun enableLocation() {
        _googleMap?.isMyLocationEnabled = true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun createRequest(permissions: Array<String>, requestCode: Int) {
        requestPermissions(permissions, requestCode)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        _marker = marker
        _presenter?.onMarkerClick(marker)
        return false
    }

    override fun onMapClick(latLon: LatLng?) {
        _presenter?.onMapClick(latLon)
    }

    override fun setMarkerInPoint(latLon: LatLng?, zoom: Float, duration: Int) {
        _marker?.isVisible = true
        _marker?.position = latLon
        if (zoom == 0F) {
            _googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLon), duration, null)
        } else {
            _googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLon, zoom), duration, null)
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        _presenter?.onLocationButtonClick()
        return false
    }

    override fun showLocation() {
        _mapClient = GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(object: GoogleApiClient.ConnectionCallbacks{
                    override fun onConnected(bundle: Bundle?) {
                        _presenter?.onLocationConnected()
                    }
                    override fun onConnectionSuspended(i: Int) {}
                }).build()
        _mapClient?.connect()
    }

    override fun requestLoadLocation(locReq: LocationRequest?) {
        LocationServices.FusedLocationApi.requestLocationUpdates(_mapClient, locReq, { location ->
            _presenter?.onLocationLoaded(location)
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        _presenter?.onRequestPermissionsResult(requestCode, grantResults)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun notifyDenied(permission: String) {
        if (shouldShowRequestPermissionRationale(permission)) {
            _presenter?.onShouldNotRequestPermissionRationale()
        } else {
            _presenter?.onShouldRequestPermissionRationale()
        }
    }

    override fun showLocationNotAllowed() {
        toast(getString(R.string.map_toast_permission_not_allowed))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            _presenter?.onActivityResult()
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun changeGeo() {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    override fun openSettings() {
        val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + packageName))
        startActivityForResult(settingsIntent, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun hasPermission() =
            checkCallingOrSelfPermission(LOCATION_PERMISSION) == PackageManager.PERMISSION_GRANTED

    override fun gpsEnable(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun choosePlaceDialog(locationName: String) {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.map_dialog_choose_place_title))
                .setMessage(locationName)
                .setPositiveButton(getString(R.string.button_choose), { _, _ -> _presenter?.onChoosePlace() })
                .setNegativeButton(getString(R.string.button_cancel), { dialog, _ -> dialog.dismiss() })
                .create().show()
    }

    override fun showTurnOnGeoDialog() {
        AlertDialog.Builder(this)
                .setMessage(getString(R.string.map_dialog_proposal_turn_on_gps))
                .setPositiveButton(getString(R.string.button_turn_on), { _,_ -> _presenter?.onTurnGeoOkClick() })
                .setNegativeButton(getString(R.string.button_cancel), { dialog,_ -> dialog.dismiss() })
                .create()
                .show()
    }

    override fun showNeedSettingsDialog() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.map_dialog_title))
                .setMessage(getString(R.string.map_dialog_explanation_settings_permission))
                .setPositiveButton(getString(R.string.button_turn_on), { _, _ -> _presenter?.onForwardSettingsOkClick() })
                .setNegativeButton(getString(R.string.button_cancel), { _, _ ->  })
                .create()
                .show()
    }

    override fun onStop() {
        _presenter?.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        _presenter?.onDestroy()
        _presenter.let { _presenter = null }
    }

    override fun closeMap() {
        startActivity<MainActivity>()
        finish()
    }

    override fun showError() {
        toast("Извините, что то пошло не так")
    }

    override fun disconnectClient() {
        _mapClient?.disconnect()
    }

    private fun initGoogleMap(googleMap: GoogleMap?) {
        _googleMap = googleMap
        _googleMap?.setOnMapClickListener(this)
        _googleMap?.setOnMyLocationButtonClickListener(this)
        _googleMap?.setOnMarkerClickListener(this)
        _marker = _googleMap?.addMarker(MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title(getString(R.string.map_marker_title))
                .visible(false)
                .draggable(true))
    }
}