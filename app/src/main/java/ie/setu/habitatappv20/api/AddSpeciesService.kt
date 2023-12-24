package ie.setu.habitatappv20.api

import ie.setu.habitatappv20.models.AddSpeciesModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddSpeciesService {
    @GET("/addSpecies")
    fun getall(): Call<List<AddSpeciesModel>>

    @GET("/addSpecies/{id}")
    fun get(@Path("id") id: String): Call<AddSpeciesModel>

    @DELETE("/addSpecies/{id}")
    fun delete(@Path("id") id: String): Call<AddSpeciesWrapper>

    @POST("/addSpecies")
    fun post(@Body addSpecies: AddSpeciesModel): Call<AddSpeciesWrapper>

    @PUT("/addSpecies/{id}")
    fun put(@Path("id") id: String,
            @Body addSpecies: AddSpeciesModel): Call<AddSpeciesWrapper>
}