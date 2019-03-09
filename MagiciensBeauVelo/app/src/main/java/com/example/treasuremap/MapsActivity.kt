package com.example.treasuremap

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Geocoder
import android.util.Log
import com.example.treasuremap.loisir.LOISIRS_LIBRES
import com.example.treasuremap.loisir.LOISIR_LIBRE
import com.example.treasuremap.loisir.Loisir
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationCallback
import com.google.gson.Gson
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var map: GoogleMap

    private var latitude :Double?= null

    private var longitude:Double?= null


    private val locationRequestCode = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        getCurrentLocation()
        // Add a marker in Sydney and move the camera
        val address = getLocationFromAddress(this, "2360, Nicolas Pinel")
        val addressB = getLocationFromAddress(this, "966, Émélie-Chamard")
        map.addMarker(MarkerOptions().position(address!!).title("My address"))
        map.addMarker(MarkerOptions().position(addressB!!).title("Vincent"))
        map.moveCamera(CameraUpdateFactory.newLatLng(address))
        setUpMap()
        DeserialiseJSONFile()



    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }

    private fun getLocationFromAddress(context: Context, strAddress: String): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var convertedAdress: LatLng? = null

        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location = address[0]
            location.getLatitude()
            location.getLongitude()

            convertedAdress = LatLng(location.getLatitude(), location.getLongitude())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return convertedAdress

    }

    private fun getCurrentLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request for permission
            Log.v("permission","i need permission")
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                locationRequestCode
            )

        } else {
            val mLocationRequest = LocationRequest.create()
            mLocationRequest.interval = 60000
            mLocationRequest.fastestInterval = 5000
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            val mLocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    if (locationResult == null) {
                        return
                    }
                    for (location in locationResult.locations) {
                        if (location != null) {

                                latitude = location.latitude
                                longitude = location.longitude
                                Log.v("location", latitude.toString())
                                Log.v("location", longitude.toString())

                            var myPosition = LatLng(latitude!!, longitude!!)
                            map.addMarker(MarkerOptions().position(myPosition).title("My position"))

                        }

                    }
                }
            }
            LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(mLocationRequest, mLocationCallback, null)



//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location: Location? ->
//                    latitude = location?.latitude
//                    longitude = location?.longitude
//
//                    }

            Log.v("permission","i got the power")
                }
    }

    fun ReadJSONFile(): String{
        var ins: InputStream = resources.openRawResource(R.raw.loisir)
        var content= ins.readBytes().toString(Charset.defaultCharset())
        Log.v("json",content)
        return content
    }
    fun DeserialiseJSONFile(){
        var gson = Gson()
        var content = ReadJSONFile()
        var jsonString = gson.fromJson(content, Loisir::class.java)

    }

}
