package ie.setu.habitatappv20.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.models.AddSpeciesModel

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_species_image.toString())
    intentLauncher.launch(chooseFile)
}
