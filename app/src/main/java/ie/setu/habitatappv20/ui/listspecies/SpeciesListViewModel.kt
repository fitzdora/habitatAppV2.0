package ie.setu.habitatappv20.ui.listspecies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.habitatappv20.models.AddSpeciesManager
import ie.setu.habitatappv20.models.AddSpeciesManager.speciesList
import ie.setu.habitatappv20.models.AddSpeciesModel
import timber.log.Timber

class SpeciesListViewModel : ViewModel() {
    private val speciesList = MutableLiveData<List<AddSpeciesModel>>()

    val observableSpeciesList: LiveData<List<AddSpeciesModel>>
        get() = speciesList

    init {
        load()
    }

    fun load() {
        try {
            AddSpeciesManager.findAll(speciesList)
            Timber.i("Retrofit Success: ${speciesList.value}")
        //speciesList.value = AddSpeciesManager.findAll()
    }
        catch (e: Exception) {
            Timber.i("Retrofit Error: ${e.message}")

        }
    }

    companion object {
        fun load() {

        }
    }

}