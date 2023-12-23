package ie.setu.habitatappv20.models

interface AddSpeciesStore {
    fun findAll(): List<AddSpeciesModel>
    fun findById(id: Long) : AddSpeciesModel?
    fun create(addSpeciesModel: AddSpeciesModel)
    fun update(addSpeciesModel: AddSpeciesModel)
}