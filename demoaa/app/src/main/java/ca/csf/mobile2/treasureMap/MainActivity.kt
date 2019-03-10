package ca.csf.mobile2.treasureMap

import android.support.v7.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.selection_activite.view.*



@EActivity(R.layout.activity_main)
class MainActivity : AppCompatActivity() {

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
    }

    @UiThread
    protected fun showMap()
    {
        indication.text = getString(R.string.trouvez)
        backButton.visibility = View.VISIBLE
    }

    @UiThread
    protected fun hideMap()
    {

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
