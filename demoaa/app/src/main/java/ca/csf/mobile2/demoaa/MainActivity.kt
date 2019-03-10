package ca.csf.mobile2.demoaa

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import ca.csf.mobile2.demoaa.Categorization.InformationProvider
import com.example.demoaa.loisir.Loisir
import com.google.gson.Gson
import org.androidannotations.annotations.*
import java.io.InputStream
import java.nio.charset.Charset
import android.widget.Button
import kotlinx.android.synthetic.main.categorie_activite_layout.view.*


@EActivity(R.layout.activity_main)
class MainActivity : AppCompatActivity() {

    var informationProvider = InformationProvider()

    var adapter: CategorieAdapter = CategorieAdapter(this, informationProvider.GetCategoriesMap())

    @ViewById(R.id.rootView)
    protected lateinit var rootView: View

    @ViewById(R.id.grid)
    protected lateinit var grid : GridView

    @ViewById(R.id.backButton)
    protected lateinit var backButton : Button

    protected lateinit var loisirObject : Loisir

    @InstanceState
    protected lateinit var selectedCategorieText : String


    @AfterViews
    protected fun afterViews() {

        grid.adapter = adapter
        loisirObject = DeserialiseJSONFile()

        grid.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Get the GridView selected/clicked item text
                selectedCategorieText = parent.getChildAt(position).categorieText.text.toString()

                Log.v("HAAAA", selectedCategorieText)

                hideCategories()
                showActivites()
            }
        }
    }

    @Background
    protected fun findSimilarWords() {

    }

    @UiThread
    protected fun showActivites() {
        backButton.visibility = View.VISIBLE
    }

    @UiThread
    protected fun hideActivites() {
        backButton.visibility = View.INVISIBLE
    }

    @UiThread
    protected fun showCategories()
    {
        grid.visibility = GridView.VISIBLE
    }


    @UiThread
    protected fun hideCategories()
    {
        grid.visibility = GridView.INVISIBLE
    }

    @Click(R.id.backButton)
    protected fun onReturnClicked()
    {
        hideActivites()
        showCategories()
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
}
