package ie.setu.habitatappv20.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddSpeciesModel(var id: Long = 0,
                           val commonName: String,
                           val scientificName: String,
                           val habitatType: String,
                           val speciesDescription: String,
                           val soilType: String = "N/A",
                           var speciesImage: Uri = Uri.EMPTY,
                           val totalSpecies: Int = 0): Parcelable

