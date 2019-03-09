package ca.csf.mobile2.demoaa.Categorization

import ca.csf.mobile2.demoaa.Dictionary
import ca.csf.mobile2.demoaa.R

class Categorizer(){


    private class Categories()
    {
        lateinit var categories : HashMap<String?,GrandesCategories>
        lateinit var imageHash : HashMap<GrandesCategories, String?>

        init {
            initializeCategoriesHashMap()

        }
        private fun initializeImageHashMap() {
            imageHash = hashMapOf(
                Pair(GrandesCategories.ACTIVITE_PHYSIQUE,R.drawable.activitephysique.toString()),
                Pair(GrandesCategories.ARTISANAT,R.drawable.artisanat.toString()),
                Pair(GrandesCategories.ARTISTE,R.drawable.artiste.toString()),
                Pair(GrandesCategories.CONFERENCE,R.drawable.Conference.toString()),
                Pair(GrandesCategories.EVENEMENT_SPECIAL,R.drawable.evenementspecial.toString()),
                Pair(GrandesCategories.EXPOSITION,R.drawable.exposition.toString()),
                Pair(GrandesCategories.LOISIR_JEUNESS,R.drawable.loisirjeunesse.toString()),
                Pair(GrandesCategories.NATATION,R.drawable.natation.toString()),
                Pair(GrandesCategories.PATRIMOINE,R.drawable.patrimoine.toString()),
                Pair(GrandesCategories.PLEIN_AIR,R.drawable.pleinair.toString()),
                Pair(GrandesCategories.SCIENTIFIQUE,R.drawable.Scientifique.toString()),
                Pair(GrandesCategories.THEATRE,R.drawable.theatre.toString()),
                Pair(GrandesCategories.MISC,R.drawable.misc.toString())
            )
        }
        private fun initializeCategoriesHashMap() {
            categories= hashMapOf(
                Pair("1R-Activité de reconnaissance",GrandesCategories.MISC),
                Pair("1R-Activités-Physiques",GrandesCategories.ACTIVITE_PHYSIQUE),
                Pair("1R-Activités aquatiques",GrandesCategories.NATATION) ,
                Pair("1R-Autre activité",GrandesCategories.MISC) ,
                Pair("1R-Conférence",GrandesCategories.CONFERENCE),
                Pair("1R-Formation", GrandesCategories.MISC),
                Pair("Activités physiques",GrandesCategories.ACTIVITE_PHYSIQUE),
                Pair("Activités aquatiques",GrandesCategories.NATATION),
                Pair("Artisanat",GrandesCategories.ARTISANAT),
                Pair("Arts de la communication",GrandesCategories.ARTISTE),
                Pair("Arts de la scène",GrandesCategories.THEATRE),
                Pair("Arts visuels",GrandesCategories.ARTISTE),
                Pair("Camp spécialisé",GrandesCategories.MISC),
                Pair("Clubs de natation",GrandesCategories.NATATION),
                Pair("Artianat",GrandesCategories.ARTISANAT),
                Pair("Exposition",GrandesCategories.EXPOSITION),
                Pair("Formation",GrandesCategories.SCIENTIFIQUE),
                Pair("Loisir éducatif",GrandesCategories.SCIENTIFIQUE),
                Pair("Loisirs jeunesse",GrandesCategories.LOISIR_JEUNESS),
                Pair("Loisirs récréatifs",GrandesCategories.MISC),
                Pair("Mieux-être",GrandesCategories.MISC),
                Pair("Métiers d'art",GrandesCategories.ARTISTE),
                Pair("Patrimoine",GrandesCategories.PATRIMOINE),
                Pair("Programme vacance été",GrandesCategories.PLEIN_AIR),
                Pair("Relâche",GrandesCategories.MISC),
                Pair("Service de garde",GrandesCategories.LOISIR_JEUNESS),
                Pair("Sports de combat",GrandesCategories.ACTIVITE_PHYSIQUE),
                Pair("Sports de glace",GrandesCategories.ACTIVITE_PHYSIQUE),
                Pair("Sports et de plein air",GrandesCategories.PLEIN_AIR),
                Pair("Événement spécial",GrandesCategories.EVENEMENT_SPECIAL),
                Pair(null,GrandesCategories.MISC)
            )
            }

    }


}