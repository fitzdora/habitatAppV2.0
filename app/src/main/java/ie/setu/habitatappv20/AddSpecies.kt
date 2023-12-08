package ie.setu.habitatappv20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber
import timber.log.Timber.Forest.i

class AddSpecies : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addspecies)
        Timber.plant(Timber.DebugTree())
        i("HabitatApp Activity Started")
    }

}
