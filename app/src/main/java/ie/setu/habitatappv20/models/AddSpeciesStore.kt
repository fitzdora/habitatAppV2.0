package ie.setu.habitatappv20.models

import androidx.lifecycle.MutableLiveData

interface AddSpeciesStore {
    fun findAll(speciesList: MutableLiveData<List<AddSpeciesModel>>)
    fun findById(id: String) : AddSpeciesModel?
    fun create(addSpeciesModel: AddSpeciesModel)

    fun delete(id: String)

   fun update(addSpeciesModel: AddSpeciesModel)
}