package ie.setu.habitatappv20.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.databinding.FragmentAddspeciesBinding
import ie.setu.habitatappv20.main.HabitatApp
import ie.setu.habitatappv20.models.AddSpeciesModel
import timber.log.Timber
class AddSpeciesFragment : Fragment() {

    private lateinit var app: HabitatApp
    private var _fragBinding: FragmentAddspeciesBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        app = activity?.application as HabitatApp
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun setHasOptionsMenu(hasMenu: Boolean) {
        super.setHasOptionsMenu(hasMenu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_addspecies, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragBinding = FragmentAddspeciesBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_addSpecies)

        fragBinding.progressBar.max = 10000
        fragBinding.addNoOfSpeciesSeen.minValue = 1
        fragBinding.addNoOfSpeciesSeen.maxValue = 1000

        fragBinding.addNoOfSpeciesSeen.setOnValueChangedListener { _, _, newVal ->
            fragBinding.amountOfSpeciesSeen.text = "$newVal"
        }

        setButtonListener(fragBinding)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
    }

    fun setButtonListener(layout: FragmentAddspeciesBinding){
        layout.buttonAddSpecies.setOnClickListener {
            val commonName = fragBinding.titleText.text.toString()
            val speciesDescription = fragBinding.speciesDescription.text.toString()
            val habitatType = fragBinding.habitatType.text.toString()
            val scientificName = fragBinding.scientificName.text.toString()

            val totalSpecies = fragBinding.addNoOfSpeciesSeen.value
                    Timber.i("Amount selected: $totalSpecies")
            if(totalSpecies >= fragBinding.progressBar.max)
                Toast.makeText(requireContext(), "No of Species Amount Exceeded!", Toast.LENGTH_LONG).show()
            else {
                val soilType: String = when (fragBinding.soilType.checkedRadioButtonId) {
                    R.id.clay -> "Clay"
                    R.id.silt -> "Silt"
                    R.id.sand -> "Sand"
                    R.id.stones -> "Stones"
                    R.id.chalky -> "Chalky"
                    else -> "N/A" // Default or handle accordingly
                }


                fragBinding.amountOfSpeciesSeen.text = getString(R.string.amountOfSpeciesSeen, totalSpecies)
                Timber.i("Formatted String: ${getString(R.string.amountOfSpeciesSeen, totalSpecies)}")
                fragBinding.progressBar.progress = totalSpecies

                app.addSpeciesStore.create(AddSpeciesModel(commonName= commonName, scientificName = scientificName, habitatType = habitatType, speciesDescription = speciesDescription, soilType = soilType, totalSpecies = totalSpecies))
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            AddSpeciesFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}