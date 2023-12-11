package ie.setu.habitatappv20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.databinding.ActivityAddspeciesBinding
import ie.setu.habitatappv20.main.HabitatApp
import ie.setu.habitatappv20.models.AddSpeciesModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class AddSpecies : AppCompatActivity() {

    private lateinit var addSpeciesLayout : ActivityAddspeciesBinding
    lateinit var app: HabitatApp

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.menu_addspecies, menu)
        return true
    }
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
        var scientificName = "scientificName"
        var speciesDescription = "speciesDescription"
        var habitatType = "habitatType"


        //Data-binding for Species Seen
        var totalSpecies = 0


        addSpeciesLayout.buttonAddSpecies.setOnClickListener {

            val commonName = addSpeciesLayout.titleText.text.toString()
            val speciesDescription = addSpeciesLayout.speciesDescription.text.toString()
            val habitatType = addSpeciesLayout.habitatType.text.toString()

            val totalSpecies = addSpeciesLayout.addNoOfSpeciesSeen.value
            Timber.i("Amount selected: $totalSpecies")
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


                addSpeciesLayout.amountOfSpeciesSeen.text = getString(R.string.amountOfSpeciesSeen, totalSpecies)
                Timber.i("Formatted String: ${getString(R.string.amountOfSpeciesSeen, totalSpecies)}")
                addSpeciesLayout.progressBar.progress = totalSpecies

                app.addSpeciesStore.create(AddSpeciesModel(commonName= commonName, scientificName = scientificName, habitatType = habitatType, speciesDescription = speciesDescription, soilType = soilType, totalSpecies = totalSpecies))
            }
        }

        Timber.plant(Timber.DebugTree())
        i("Total No. Of Species Seen: $totalSpecies ")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //set up different menu choices
        return when (item.itemId){
            R.id.action_showSpeciesList -> {
                startActivity(Intent(this, SpeciesList::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
            }
        }
    }
