package ca.csf.mobile2.demoaa

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import org.androidannotations.annotations.*
import java.util.Dictionary

@EActivity(R.layout.activity_main)
class MainActivity : AppCompatActivity() {

    var map = hashMapOf<String, Int>(
        Pair("allo", R.drawable.ic_launcher_foreground),
        Pair("qwd", R.drawable.ic_launcher_background),
        Pair("alvergwrlo", R.drawable.ic_launcher_foreground),
        Pair("albrwbtwlo", R.drawable.ic_launcher_background),
        Pair("alsdfdafjhlo", R.drawable.ic_launcher_foreground),
        Pair("jyjuil;", R.drawable.ic_launcher_background),
        Pair(";po;iokjyhtrg", R.drawable.ic_launcher_foreground),
        Pair("allsrhtyjkufo", R.drawable.ic_launcher_background),
        Pair("lgiukfyj", R.drawable.ic_launcher_foreground),
        Pair("srthdykful", R.drawable.ic_launcher_background),
        Pair(";oglifk", R.drawable.ic_launcher_foreground),
        Pair("dyjfku", R.drawable.ic_launcher_background),
        Pair("dytjfuk", R.drawable.ic_launcher_foreground),
        Pair("dytjfuki", R.drawable.ic_launcher_background),
        Pair("`pjo;ilu", R.drawable.ic_launcher_foreground),
        Pair("artsy", R.drawable.ic_launcher_background),
        Pair("olgifku", R.drawable.ic_launcher_foreground),
        Pair("aetrystdy", R.drawable.ic_launcher_background),
        Pair("dyhzdgrayr", R.drawable.ic_launcher_foreground)
    )

    var adapter: CategorieAdapter = CategorieAdapter(this, map)

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
