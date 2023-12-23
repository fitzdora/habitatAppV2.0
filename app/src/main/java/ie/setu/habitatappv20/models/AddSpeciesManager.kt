package ie.setu.habitatappv20.models

import timber.log.Timber

var lastId = 0L

internal  fun getId(): Long {
    return lastId++
}
object AddSpeciesManager : AddSpeciesStore {
    val speciesList = ArrayList<AddSpeciesModel>()

    override fun findAll(): List<AddSpeciesModel> {
        return speciesList
    }

    override fun findById(id: Long): AddSpeciesModel? {
        val foundSpecies: AddSpeciesModel? = speciesList.find { it.id == id}
        return foundSpecies

    }

    override fun create(addSpeciesModel: AddSpeciesModel) {
        addSpeciesModel.id = getId()
        speciesList.add(addSpeciesModel)
        logAll()
    }

    override fun update(addSpeciesModel: AddSpeciesModel) {
        var foundSpecies: AddSpeciesModel? = speciesList.find { s -> s.id == addSpeciesModel.id}
        if(foundSpecies != null){
            foundSpecies.id = addSpeciesModel.id
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