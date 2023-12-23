package ie.setu.habitatappv20.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddSpeciesModel(var id: Long = 0,
                           var commonName: String,
                           var scientificName: String,
                           var habitatType: String,
                           var speciesDescription: String,
                           var soilType: String = "N/A",
                           var speciesImage: Uri = Uri.EMPTY,
                           var totalSpecies: Int = 0): Parcelable

