package ie.setu.habitatappv20.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.databinding.ActivityAddspeciesBinding
import ie.setu.habitatappv20.main.HabitatApp
import timber.log.Timber
import timber.log.Timber.Forest.i

class AddSpecies : AppCompatActivity() {

    private lateinit var addSpeciesLayout : ActivityAddspeciesBinding
    lateinit var app: HabitatApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addSpeciesLayout = ActivityAddspeciesBinding.inflate(layoutInflater)
        setContentView(addSpeciesLayout.root)

        app = this.application as HabitatApp

        addSpeciesLayout.progressBar.max = 10000

        addSpeciesLayout.addNoOfSpeciesSeen.minValue = 1
        addSpeciesLayout.addNoOfSpeciesSeen.maxValue = 1000

        addSpeciesLayout.addNoOfSpeciesSeen.setOnValueChangedListener { _, _, newVal ->
            Log.i("AddSpecies", "New value selected: $newVal")
            addSpeciesLayout.amountOfSpeciesSeen.text = newVal.toString()
        }

        //DataBinding for EditView
        var commonName = "text_title"
        var speciesDescription = "speciesDescription"
        var habitatType = "habitatType"


        //Data-binding for Species Seen
        var totalSpecies = 0


        addSpeciesLayout.buttonAddTitle.setOnClickListener {

            commonName = addSpeciesLayout.textTitle.text.toString()
            speciesDescription = addSpeciesLayout.speciesDescription.text.toString()
            habitatType = addSpeciesLayout.habitatType.text.toString()

            val amount = addSpeciesLayout.addNoOfSpeciesSeen.value
            Timber.i("Amount selected: $amount")
            if(totalSpecies >= addSpeciesLayout.progressBar.max)
                Toast.makeText(applicationContext, "No of Species Amount Exceeded!", Toast.LENGTH_LONG).show()
            else {
                val soilType: String = when (addSpeciesLayout.soilType.checkedRadioButtonId) {
                    R.id.clay -> "Clay"
                    R.id.silt -> "Silt"
                    R.id.sand -> "Sand"
                    R.id.stones -> "Stones"
                    R.id.chalky -> "Chalky"
                    else -> "N/A" // Default or handle accordingly
                }

                totalSpecies += amount
                Timber.i("Formatted String: ${getString(R.string.amountOfSpeciesSeen, totalSpecies)}")
                addSpeciesLayout.amountOfSpeciesSeen.text = getString(R.string.amountOfSpeciesSeen, totalSpecies)
                addSpeciesLayout.progressBar.progress = totalSpecies
            }
        }

        Timber.plant(Timber.DebugTree())
        i("Total No. Of Species Seen: $totalSpecies ")
    }

}
