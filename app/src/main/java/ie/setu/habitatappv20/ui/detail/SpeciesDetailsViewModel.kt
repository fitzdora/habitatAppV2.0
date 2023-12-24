package ie.setu.habitatappv20.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.habitatappv20.models.AddSpeciesManager
import ie.setu.habitatappv20.models.AddSpeciesModel

class SpeciesDetailsViewModel : ViewModel() {
    private val species = MutableLiveData<AddSpeciesModel>()

    val observableSpecies: LiveData<AddSpeciesModel>
        get()= species

    fun getSpecies(id: String) {
        species.value = AddSpeciesManager.findById(id)
    }
}