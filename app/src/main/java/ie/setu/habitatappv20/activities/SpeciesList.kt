package ie.setu.habitatappv20.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ie.setu.habitatappv20.R

class SpeciesList : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_specieslist, menu)
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_species_list)
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