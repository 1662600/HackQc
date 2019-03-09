package ca.csf.mobile2.demoaa

import android.content.Context
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean

@EBean(scope = EBean.Scope.Singleton)
class Dictionary(
    private val context: Context
) {

    @Bean
    protected lateinit var comparator: LevenshteinDistance
    private val words: List<String>

    init {
        words = context.resources.openRawResource(R.raw.francais).use {
            it.reader().readLines()
        }
    }

    fun find(word: String, max: Int): List<String> {
        return words.asSequence()
            .map { Pair(it, comparator.compare(it, word)) }
            .sortedBy { it.second }
            .take(max)
            .map { it.first }
            .toList()
    }

}
