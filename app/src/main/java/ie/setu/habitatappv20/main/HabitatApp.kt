package ie.setu.habitatappv20.main

import android.app.Application
import ie.setu.habitatappv20.models.AddSpeciesMemStore
import ie.setu.habitatappv20.models.AddSpeciesStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class HabitatApp : Application() {

    lateinit var addSpeciesStore: AddSpeciesStore
    override fun onCreate() {
        super.onCreate()
        addSpeciesStore = AddSpeciesMemStore()
        Timber.plant(Timber.DebugTree())
        i("HabitatApp Application Started")
    }
}