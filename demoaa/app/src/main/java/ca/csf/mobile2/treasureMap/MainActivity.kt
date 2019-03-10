package ca.csf.mobile2.treasureMap

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.util.Log
import android.view.View

import android.widget.GridView
import ca.csf.mobile2.treasureMap.categorization.InformationProvider
import org.androidannotations.annotations.*

import android.widget.*
import ca.csf.mobile2.treasureMap.ActivityAdapter

import ca.csf.mobile2.treasureMap.loisir.Loisir
import com.google.gson.Gson
import org.androidannotations.annotations.*
import java.io.InputStream
import java.nio.charset.Charset
import ca.csf.mobile2.treasureMap.loisir.LOISIR_LIBRE
import kotlinx.android.synthetic.main.categorie_activite_layout.view.*
import ca.csf.mobile2.treasureMap.categorization.Categories
import ca.csf.mobile2.treasureMap.map.MapsActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.selection_activite.view.*



@EActivity(R.layout.activity_main)
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var map: GoogleMap

    private var latitude :Double?= null

    private var longitude:Double?= null


    private val locationRequestCode = 1000

    var adresseSelected = ""

    var activitiesWithSelectedCategories : List<LOISIR_LIBRE> = listOf()

    var informationProvider = InformationProvider()

    var adapter: CategorieAdapter = CategorieAdapter(this, informationProvider.GetCategoriesMap())

    lateinit var activityAdapter: ActivityAdapter

    @ViewById(R.id.rootView)
    protected lateinit var rootView: View

    @ViewById(R.id.grid)
    protected lateinit var grid : GridView

    @ViewById(R.id.list)
    protected lateinit var list : ListView

    @ViewById(R.id.mapFrame)
    protected lateinit var gMap : FrameLayout

    @ViewById(R.id.backButton)
    protected lateinit var backButton : Button

    @ViewById(R.id.indication)
    protected lateinit var indication : TextView

    protected lateinit var loisirObject : Loisir

    @InstanceState
    protected lateinit var selectedCategorieText : String


    @AfterViews
    protected fun afterViews()
    {

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.gmap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        grid.adapter = adapter

        loisirObject = DeserialiseJSONFile()

        grid.onItemClickListener = object : AdapterView.OnItemClickListener
        {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long)
            {
                // Get the GridView selected/clicked item text
                if(view!=null)
                {
                    selectedCategorieText = view.categorieText.text.toString()
                }

                hideCategories()
                showActivites()
            }
        }

        list.onItemClickListener = object : AdapterView.OnItemClickListener
        {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long)
            {
                // Get the GridView selected/clicked item text
                if(view!=null)
                {
                    adresseSelected = view.adresse.text.toString()
                }

                hideActivites()
                showMap()
            }
        }

        hideMap()
    }

    @UiThread
    protected fun showMap()
    {
        indication.text = getString(R.string.trouvez)
        backButton.visibility = View.VISIBLE
        gMap.visibility = View.VISIBLE
    }

    @UiThread
    protected fun hideMap()
    {
        gMap.visibility = View.INVISIBLE
    }

    @UiThread
    protected fun showActivites()
    {
        backButton.visibility = View.VISIBLE
        activitiesWithSelectedCategories = GetAllActivitiesWithSelectedCategorie()
        indication.text = getString(R.string.activite)

        activityAdapter = ActivityAdapter(this, activitiesWithSelectedCategories)
        list.adapter = activityAdapter

        list.visibility = View.VISIBLE
    }

    @UiThread
    protected fun hideActivites()
    {
        backButton.visibility = View.INVISIBLE
        list.visibility = View.INVISIBLE
    }

    @UiThread
    protected fun showCategories()
    {
        grid.visibility = GridView.VISIBLE
        indication.text = getString(R.string.categorie)
    }


    @UiThread
    protected fun hideCategories()
    {
        grid.visibility = View.INVISIBLE
    }

    @Click(R.id.backButton)
    protected fun onReturnClicked()
    {
        if(list.visibility == View.VISIBLE)
        {
            hideActivites()
            showCategories()
        }
        else
        {
            showActivites()
            hideMap()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        getCurrentLocation()

        val address = getLocationFromAddress(this, "2360, Nicolas Pinel")
        val addressB = getLocationFromAddress(this, "966, Émélie-Chamard")
        map.addMarker(MarkerOptions().position(address!!).title("My address"))
        map.addMarker(MarkerOptions().position(addressB!!).title("Vincent"))


        map.moveCamera(CameraUpdateFactory.newLatLng(address))
        setUpMap()
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), MainActivity.LOCATION_PERMISSION_REQUEST_CODE
            )
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

        }
    }

    protected fun ReadJSONFile(): String
    {
        var ins: InputStream = resources.openRawResource(R.raw.loisir)
        var content= ins.readBytes().toString(Charset.defaultCharset())
        return content
    }

    protected fun DeserialiseJSONFile() : Loisir
    {
        var gson = Gson()
        var content = ReadJSONFile()

        return gson.fromJson(content, Loisir::class.java)
    }

    private fun GetAllActivitiesWithSelectedCategorie() : List<LOISIR_LIBRE>
    {
        var subCategories: HashMap<String?, Categories> = informationProvider.GetSubCategoriesOf(Categories.valueOf(selectedCategorieText))

        var activites : List<LOISIR_LIBRE> = loisirObject.LOISIRS_LIBRES.LOISIR_LIBRE

        var activitiesFiltre : List<LOISIR_LIBRE> = listOf()

        for(i in 0..subCategories.size -1)
        {
                activitiesFiltre += activites.filter { it.DESCRIPTION_NAT == subCategories.keys.elementAt(i) }
        }

        activitiesFiltre = activitiesFiltre.distinctBy {it.ADRESSE}

        return activitiesFiltre
    }
}
