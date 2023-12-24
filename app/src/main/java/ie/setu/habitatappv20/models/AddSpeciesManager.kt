package ie.setu.habitatappv20.models

import androidx.lifecycle.MutableLiveData
import ie.setu.habitatappv20.api.AddSpeciesClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

var lastId = 0L

internal  fun getId(): Long {
    return lastId++
}
object AddSpeciesManager : AddSpeciesStore {

    val speciesList = ArrayList<AddSpeciesModel>()

    override fun findAll(speciesList: MutableLiveData<List<AddSpeciesModel>>) {
        val call = AddSpeciesClient.getApi().getall()

        call.enqueue(object : Callback<List<AddSpeciesModel>> {
            override fun onResponse(
                call: Call<List<AddSpeciesModel>>, response: Response<List<AddSpeciesModel>>
            ) {
                speciesList.value = response.body() as ArrayList<AddSpeciesModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<AddSpeciesModel>>, t: Throwable) {
                Timber.i("Retrofit Error: $t.message")
            }
        })
    }

    override fun findById(id: String): AddSpeciesModel? {
        val foundSpecies: AddSpeciesModel? = speciesList.find { it._id == id}
        return foundSpecies

    }

    override fun create(addSpeciesModel: AddSpeciesModel) {
        addSpeciesModel._id = getId().toString()
        speciesList.add(addSpeciesModel)
        logAll()
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun update(addSpeciesModel: AddSpeciesModel) {
        var foundSpecies: AddSpeciesModel? = speciesList.find { s -> s._id == addSpeciesModel._id}
        if(foundSpecies != null){
            foundSpecies._id = addSpeciesModel._id
            foundSpecies.commonName = addSpeciesModel.commonName
            foundSpecies.scientificName = addSpeciesModel.scientificName
            foundSpecies.speciesDescription = addSpeciesModel.speciesDescription
            foundSpecies.habitatType = addSpeciesModel.habitatType
            foundSpecies.soilType = addSpeciesModel.soilType
            foundSpecies.totalSpecies = addSpeciesModel.totalSpecies
            foundSpecies.speciesImage = addSpeciesModel.speciesImage
            logAll()
        }
    }

    fun logAll() {
        Timber.v("** Species List **")
        speciesList.forEach{Timber.v("Species ${it}")}
    }
}