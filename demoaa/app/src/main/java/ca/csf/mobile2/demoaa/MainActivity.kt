package ca.csf.mobile2.demoaa

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.androidannotations.annotations.*

private const val NB_MAX_WORDS = 5
private const val LINE_SEPARATOR = "\n"

@EActivity(R.layout.activity_main)
class MainActivity : AppCompatActivity() {

    @ViewById(R.id.rootView)
    protected lateinit var rootView: View

    @InstanceState
    @JvmField
    protected final var results = ArrayList<String>()

    @AfterViews
    protected fun afterViews() {
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
