package ru.aol_panchenko.weatherapp.presentation.map_screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ru.aol_panchenko.weatherapp.R
import android.widget.Toast
import android.support.design.widget.CoordinatorLayout.Behavior.setTag




/**
 * Created by Panchenko.AO on 18.10.2017.
 */
class MapActivity : AppCompatActivity(), MapMVPView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private val PERTH = LatLng(-31.952854, 115.857342)
    private val SYDNEY = LatLng(-33.87365, 151.20689)
    private val BRISBANE = LatLng(-27.47093, 153.0235)

    private lateinit var _presenter: MapPresenter
    private var _googleMap: GoogleMap? = null
    private var _perth: Marker? = null
    private var _sydney: Marker? = null
    private var _brisbane: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maps_activity)
        _presenter = MapPresenter(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        _googleMap = googleMap
        _perth = _googleMap?.addMarker(MarkerOptions()
                .position(PERTH)
                .title("Perth"))
        _perth?.tag = 0


        _sydney = _googleMap?.addMarker(MarkerOptions()
                .position(SYDNEY)
                .title("Sydney"))
        _sydney?.tag = 0

        _brisbane = _googleMap?.addMarker(MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane"))
        _brisbane?.tag = 0

        // Set a listener for marker click.
        _googleMap?.setOnMarkerClickListener(this);
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        var clickCount:Int? = marker?.getTag() as Int

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount!! + 1
            marker.tag = clickCount
            Toast.makeText(this, marker.title + " has been clicked " + clickCount + " times.", Toast.LENGTH_SHORT).show()
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }

}