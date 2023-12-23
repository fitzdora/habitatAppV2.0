package ie.setu.habitatappv20.ui.addspecies

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.habitatappv20.models.AddSpeciesManager
import ie.setu.habitatappv20.models.AddSpeciesModel
import java.lang.IllegalArgumentException

class AddSpeciesViewModel : ViewModel() {


    private val status = MutableLiveData<Boolean>().apply { value = false }

    // Use MutableLiveData to represent an observable Uri
    private val _speciesImage = MutableLiveData<Uri>()
    val speciesImage: LiveData<Uri> get() = _speciesImage
    val observableStatus: LiveData<Boolean>
        get() = status

    fun addSpecies(addSpecies: AddSpeciesModel) {
        status.value = try {
            AddSpeciesManager.create(addSpecies)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }

    // Use this method to update the value of _speciesImage
    fun setImage(uri: Uri) {
        _speciesImage.value = uri
    }

}