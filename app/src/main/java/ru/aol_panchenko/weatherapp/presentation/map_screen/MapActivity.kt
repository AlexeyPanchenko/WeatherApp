package ru.aol_panchenko.weatherapp.presentation.map_screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
import org.jetbrains.anko.toast
import ru.aol_panchenko.weatherapp.R
import java.util.*

/**
 * Created by Panchenko.AO on 18.10.2017.
 */
class MapActivity : AppCompatActivity(), MapMVPView,
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private val PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION

    private lateinit var _presenter: MapPresenter
    private var _googleMap: GoogleMap? = null
    private var _marker: Marker? = null
    private var _mapClient: GoogleApiClient? = null
    private var _geocoder: Geocoder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maps_activity)
        _presenter = MapPresenter(this)
        _geocoder = Geocoder(this, Locale.getDefault())
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        initGoogleMap(googleMap)
        if (hasPermission()) {
            showLocation()
        } else {
            createRequest()
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        val locationName = extractLocationName(marker)
        AlertDialog.Builder(this)
                .setTitle("Выбор местности")
                .setMessage(locationName)
                .setPositiveButton("Выбрать", { _, _ -> toast("Да") })
                .setNegativeButton("Отмена", { dialog, _ -> dialog.dismiss() })
                .create().show()
        return false
    }

    override fun onMapClick(latLon: LatLng?) {
        if (latLon?.latitude != null) {
            _marker?.isVisible = true
            _marker?.position = LatLng(latLon.latitude, latLon.longitude)
            _googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLon), 1000, null)
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (enabled) {
            _mapClient = GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(object: GoogleApiClient.ConnectionCallbacks{
                        override fun onConnected(bundle: Bundle?) {

                            val locReq = LocationRequest.create()
                            locReq.priority = LocationRequest.PRIORITY_LOW_POWER
                            LocationServices.FusedLocationApi.requestLocationUpdates(_mapClient, locReq, {location ->
                                Log.d("TTT", "MY location ${location.latitude}   ${location.longitude}")

                                _marker?.isVisible = true
                                _marker?.position = LatLng(location.latitude, location.longitude)
                                _googleMap?.animateCamera(CameraUpdateFactory.
                                        newLatLngZoom(LatLng(location.latitude, location.longitude), 15F), 2500, null)
                            })

                        }

                        override fun onConnectionSuspended(i: Int) {
                        }
                    }).build()
            _mapClient?.connect()
        } else {
            AlertDialog.Builder(this)
                    .setMessage("Для определения текущего местоположения необходимо включить геолокацию")
                    .create()
                    .show()
        }

        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        var allowed = true
        when(requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> { grantResults.forEach { allowed = allowed && (it == PackageManager.PERMISSION_GRANTED) }}
            else -> allowed = false
        }
        if (allowed) {
            showLocation()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(PERMISSION)) {
                    toast("Геолокация недоступна")
                } else {
                    AlertDialog.Builder(this)
                            .setTitle("Геолокация")
                            .setMessage("Для определения текущего местоположения, " +
                                    "необходимо включить разрешение на геолокацию в настройках приложения")
                            .setPositiveButton("Включить", { _,_ -> openSettings()})
                            .setNegativeButton("Отмена", { dialog,_ -> dialog.dismiss() })
                            .create()
                            .show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            showLocation()
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initGoogleMap(googleMap: GoogleMap?) {
        _googleMap = googleMap
        _googleMap?.setOnMapClickListener(this)
        _googleMap?.setOnMyLocationButtonClickListener(this)
        _googleMap?.setOnMarkerClickListener(this)
        _marker = _googleMap?.addMarker(MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Погода")
                .visible(false)
                .draggable(true))
    }

    private fun openSettings() {
        val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + packageName))
        startActivityForResult(settingsIntent, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun hasPermission() =
            checkCallingOrSelfPermission(PERMISSION) == PackageManager.PERMISSION_GRANTED


    private fun showLocation() {
        _googleMap?.isMyLocationEnabled = true
    }

    private fun extractLocationName(marker: Marker?): String {
        val addresses = _geocoder?.getFromLocation(marker?.position?.latitude!!, marker.position?.longitude!!, 1)
        val address = if (addresses!!.isNotEmpty()) addresses[0] else null
        val county = if (!address?.countryName.isNullOrEmpty()) address?.countryName else ""
        val adminArea = if (!address?.adminArea.isNullOrEmpty()) address?.adminArea else ""
        val subAdminArea = if (!address?.subAdminArea.isNullOrEmpty()) address?.subAdminArea else ""
        return "$county $adminArea $subAdminArea"
    }

    private fun createRequest() {
        val permissions = arrayOf(PERMISSION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onStop() {
        _mapClient?.disconnect()
        super.onStop()
    }

}