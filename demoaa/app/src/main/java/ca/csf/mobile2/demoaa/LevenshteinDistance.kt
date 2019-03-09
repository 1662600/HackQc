package ca.csf.mobile2.demoaa

import org.androidannotations.annotations.EBean

private const val DISTANCES_ARRAY_SIZE = 1025

@EBean(scope = EBean.Scope.Singleton)
class LevenshteinDistance {

    private val distances = Array(DISTANCES_ARRAY_SIZE) { IntArray(DISTANCES_ARRAY_SIZE) }

    fun compare(string1: String, string2: String): Int {
        val string1Length = string1.length
        val string2Length = string2.length

        if (string1Length > distances.size - 1 || string2Length > distances.size - 1) {
            throw IllegalArgumentException("Input string must be smaller than the configured max word size.")
        }

        val distancesSizeI = string1Length + 1
        val distancesSizeJ = string2Length + 1

        for (i in 0 until distancesSizeI) {
            distances[i][0] = i
        }
        for (j in 0 until distancesSizeJ) {
            distances[0][j] = j
        }

        for (i in 1 until distancesSizeI) {
            for (j in 1 until distancesSizeJ) {
                val substitutionCost = if (string1[i - 1] == string2[j - 1]) 0 else 1

                distances[i][j] = minOf(
                    distances[i - 1][j] + 1,
                    distances[i][j - 1] + 1,
                    distances[i - 1][j - 1] + substitutionCost
                )

            }
        }

        return distances[distancesSizeI - 1][distancesSizeJ - 1]
    }

}
