package ie.setu.habitatappv20.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddSpeciesModel(var id: Long = 0,
                           val commonName: String,
                           val habitatType: String,
                           val speciesDescription: String,
                           val soilType: String = "N/A",
                           val total: Int = 0): Parcelable

