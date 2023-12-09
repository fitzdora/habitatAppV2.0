package ie.setu.habitatappv20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ie.setu.habitatappv20.databinding.ActivityAddspeciesBinding
import timber.log.Timber
import timber.log.Timber.Forest.i

class AddSpecies : AppCompatActivity() {

    private lateinit var addSpeciesLayout : ActivityAddspeciesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_addspecies)
        addSpeciesLayout = ActivityAddspeciesBinding.inflate(layoutInflater)
        setContentView(addSpeciesLayout.root)

        addSpeciesLayout.progressBar.max = 10000

        addSpeciesLayout.addNoOfSpeciesSeen.minValue = 1
        addSpeciesLayout.addNoOfSpeciesSeen.maxValue = 1000

        addSpeciesLayout.addNoOfSpeciesSeen.setOnValueChangedListener { _, _, newVal ->
            addSpeciesLayout.TotalAmountOfSpeciesSeen.setText("$newVal")
        }

        //Data-binding for Species Seen
        var totalSpecies = 0

        addSpeciesLayout.buttonAddTitle.setOnClickListener {
            val amount = if (addSpeciesLayout.TotalAmountOfSpeciesSeen.text.isNotEmpty())
                addSpeciesLayout.TotalAmountOfSpeciesSeen.text.toString().toInt() else addSpeciesLayout.addNoOfSpeciesSeen.value
            if(totalSpecies >= addSpeciesLayout.progressBar.max)
                Toast.makeText(applicationContext, "No of Species Amount Exceeded!", Toast.LENGTH_LONG).show()
            else {
                totalSpecies += amount
                addSpeciesLayout.TotalAmountOfSpeciesSeen.text = getString(totalSpecies)
                addSpeciesLayout.progressBar.progress = totalSpecies
            }
        }


        Timber.plant(Timber.DebugTree())
        i("HabitatApp Activity Started")
    }

}
