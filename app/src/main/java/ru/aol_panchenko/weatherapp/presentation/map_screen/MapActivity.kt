package ru.aol_panchenko.weatherapp.presentation.map_screen

import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ru.aol_panchenko.weatherapp.R
import org.jetbrains.anko.toast
import android.support.v7.app.AlertDialog
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener


/**
 * Created by Panchenko.AO on 18.10.2017.
 */
class MapActivity : AppCompatActivity(), MapMVPView,
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMyLocationClickListener {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private lateinit var _presenter: MapPresenter
    private var _googleMap: GoogleMap? = null
    private var _marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maps_activity)
        _presenter = MapPresenter(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        _googleMap = googleMap
        _googleMap?.setOnMapClickListener(this)
        _googleMap?.setOnMyLocationClickListener(this)
        _googleMap?.isMyLocationEnabled = true
        _marker = _googleMap?.addMarker(MarkerOptions()
                .position(LatLng(55.8156, 37.9647))
                .title("Погода")
                .visible(false)
                .draggable(true))
        _googleMap?.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        AlertDialog.Builder(this)
                .setTitle("Выбрать позицию?")
                .setMessage("Хотите посмотреть погоду в месте с координатами: " +
                        "${marker?.position?.latitude?.toFloat()}, ${marker?.position?.longitude?.toFloat()}?")
                .setPositiveButton("Да", { _, _ -> toast("Да") })
                .setNegativeButton("Нет", { dialog, _ -> dialog.dismiss() })
                .create().show()
        return false
    }

    override fun onMapClick(latLon: LatLng?) {
        _marker?.isVisible = true
        _marker?.position = LatLng(latLon?.latitude!!, latLon.longitude)
        _googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLon), 1000, null)
    }

    override fun onMyLocationClick(location: Location) {
        Log.d("TTT", "MY location ${location.latitude}   ${location.longitude}")
        _marker?.isVisible = true
        _marker?.position = LatLng(location.latitude, location.longitude)
        _googleMap?.animateCamera(CameraUpdateFactory.
                newLatLng(LatLng(location.latitude, location.longitude)), 1000, null)
    }


}