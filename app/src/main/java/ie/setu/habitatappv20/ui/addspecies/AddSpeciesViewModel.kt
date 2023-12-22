package ie.setu.habitatappv20.ui.addspecies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.habitatappv20.models.AddSpeciesManager
import ie.setu.habitatappv20.models.AddSpeciesModel
import java.lang.IllegalArgumentException

class AddSpeciesViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>().apply { value = false }

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addSpecies(species: AddSpeciesModel) {
        status.value = try {
            AddSpeciesManager.create(species)
            true
        } catch (e: IllegalArgumentException){
            false
        }
    }
}