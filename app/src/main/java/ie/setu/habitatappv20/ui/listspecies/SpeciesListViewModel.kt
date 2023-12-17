package ie.setu.habitatappv20.ui.listspecies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.habitatappv20.models.AddSpeciesManager
import ie.setu.habitatappv20.models.AddSpeciesManager.speciesList
import ie.setu.habitatappv20.models.AddSpeciesModel

class SpeciesListViewModel : ViewModel() {
    private val speciesList = MutableLiveData<List<AddSpeciesModel>>()

    val observableSpeciesList: LiveData<List<AddSpeciesModel>>
        get() = speciesList

    init {
        load()
    }

    fun load() {
        speciesList.value = AddSpeciesManager.findAll()
    }

    companion object {
        fun load() {

        }
    }

}