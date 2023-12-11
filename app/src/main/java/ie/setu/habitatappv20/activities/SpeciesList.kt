package ie.setu.habitatappv20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.adapters.AddSpeciesAdapter
import ie.setu.habitatappv20.databinding.ActivityAddspeciesBinding
import ie.setu.habitatappv20.main.HabitatApp
import androidx.recyclerview.widget.RecyclerView
import ie.setu.habitatappv20.databinding.ActivitySpecieslistBinding

class SpeciesList : AppCompatActivity() {

    lateinit var app: HabitatApp
    lateinit var speciesListLayout : ActivitySpecieslistBinding
    // lateinit var speciesListLayout : ActivityAddspeciesBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_specieslist, menu) //may need to double check menu here
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_species_list)

        speciesListLayout = ActivitySpecieslistBinding.inflate(layoutInflater)
        setContentView(speciesListLayout.root)

        app = this.application as HabitatApp
        speciesListLayout.recyclerView.layoutManager = LinearLayoutManager(this)
        speciesListLayout.recyclerView.adapter = AddSpeciesAdapter(app.addSpeciesStore.findAll())

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //set up different menu choices
        return when (item.itemId){
            R.id.action_addSpecies -> {
                startActivity(Intent(this, AddSpecies::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}