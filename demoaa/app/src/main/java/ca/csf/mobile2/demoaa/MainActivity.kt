package ca.csf.mobile2.demoaa

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.GridView
import ca.csf.mobile2.demoaa.Categorization.InformationProvider
import org.androidannotations.annotations.*
import java.util.Dictionary

@EActivity(R.layout.activity_main)
class MainActivity : AppCompatActivity() {

    var informationProvider = InformationProvider()

    var adapter: CategorieAdapter = CategorieAdapter(this, informationProvider.GetCategoriesMap())

    @ViewById(R.id.rootView)
    protected lateinit var rootView: View

    @ViewById(R.id.grid)
    protected lateinit var grid : GridView

    @InstanceState
    @JvmField
    protected final var results = ArrayList<String>()

    @AfterViews
    protected fun afterViews() {


        grid.adapter = adapter
        showWords(results)
    }



    @Click(R.id.searchButton)
    protected fun onSearchButtonClicked() {
    }

    @Background
    protected fun findSimilarWords() {

    }

    @UiThread
    protected fun showWords(words: List<String>) {

    }
}
