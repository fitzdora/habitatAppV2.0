package ie.setu.habitatappv20.ui.addspecies

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.squareup.picasso.Picasso
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.databinding.FragmentAddspeciesBinding
import ie.setu.habitatappv20.helpers.showImagePicker
import ie.setu.habitatappv20.models.AddSpeciesModel
import timber.log.Timber
class AddSpeciesFragment : Fragment() {


    //private lateinit var app: HabitatApp
    private var _fragBinding: FragmentAddspeciesBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var addSpeciesViewModel: AddSpeciesViewModel
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //app = activity?.application as HabitatApp
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragBinding = FragmentAddspeciesBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_addSpecies)

        //MVVM model refactor
        setupMenu()

        //image calling
        registerImagePickerCallback()


        addSpeciesViewModel = ViewModelProvider(this).get(AddSpeciesViewModel::class.java)
        addSpeciesViewModel.observableStatus.observe(viewLifecycleOwner, Observer { status ->
            status?.let { render(status) }
        })


        addSpeciesViewModel.speciesImage.observe(viewLifecycleOwner, Observer { uri ->
            // Update UI with the new image URI using Picasso
            Picasso.get().load(uri).into(fragBinding.speciesImage)
        })

        fragBinding.progressBar.max = 10000
        fragBinding.addNoOfSpeciesSeen.minValue = 1
        fragBinding.addNoOfSpeciesSeen.maxValue = 1000

        fragBinding.addNoOfSpeciesSeen.setOnValueChangedListener { _, _, newVal ->
            fragBinding.amountOfSpeciesSeen.text = "$newVal"
        }

        if (requireActivity().intent.hasExtra("addSpecies_edit")) {
            var edit = true
            addSpeciesViewModel = requireActivity().intent.extras?.getParcelable("addSpecies_edit")!!
            fragBinding.buttonAddImage.text = if (edit) getString(R.string.change_species_image) else getString(R.string.select_species_image)
            if(addSpeciesViewModel.speciesImage != Uri.EMPTY) {
                Picasso.get()
                    .load(selectedImageUri)
                    .into(fragBinding.speciesImage)
            }
        }

        setButtonListener(fragBinding)
        return root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                //super.onPrepareMenu(menu)
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_addspecies, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return NavigationUI.onNavDestinationSelected(
                    menuItem,
                    requireView().findNavController()
                )
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    findNavController().popBackStack()
                }
            }

            false -> Toast.makeText(context, getString(R.string.addSpeciesError), Toast.LENGTH_LONG)
                .show()
        }
    }


    fun setButtonListener(layout: FragmentAddspeciesBinding) {
        layout.buttonAddSpecies.setOnClickListener {
            val commonName = fragBinding.titleText.text.toString()
            val speciesDescription = fragBinding.speciesDescription.text.toString()
            val habitatType = fragBinding.habitatType.text.toString()
            val scientificName = fragBinding.scientificName.text.toString()

            /* val totalSpecies = fragBinding.addNoOfSpeciesSeen.value
            Timber.i("Amount selected: $totalSpecies") */

            val totalSpecies = layout.addNoOfSpeciesSeen.value
            if (totalSpecies >= fragBinding.progressBar.max)
                Toast.makeText(
                    requireContext(),
                    "No of Species Amount Exceeded!",
                    Toast.LENGTH_LONG
                ).show()
            else {
                val soilType: String = when (layout.soilType.checkedRadioButtonId) {
                    R.id.clay -> "Clay"
                    R.id.silt -> "Silt"
                    R.id.sand -> "Sand"
                    R.id.stones -> "Stones"
                    R.id.chalky -> "Chalky"
                    else -> "N/A" // Default or handle accordingly
                }

                //Initialize model
               addSpeciesViewModel.addSpecies(
                    AddSpeciesModel(
                        commonName = commonName,
                        scientificName = scientificName,
                        speciesDescription = speciesDescription,
                        habitatType = habitatType,
                        soilType = soilType,
                        totalSpecies = totalSpecies,
                        speciesImage = Uri.EMPTY
                    )
                )


                layout.amountOfSpeciesSeen.text =
                    getString(R.string.amountOfSpeciesSeen, totalSpecies)
                Timber.i(
                    "Formatted String: ${
                        getString(
                            R.string.amountOfSpeciesSeen,
                            totalSpecies
                        )
                    }"
                )
                layout.progressBar.progress = totalSpecies
            }

        }

        layout.buttonAddImage.setOnClickListener {
            Timber.i("Select image")
            showImagePicker(imageIntentLauncher)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        val addSpeciesViewModel = ViewModelProvider(this).get(AddSpeciesViewModel::class.java)
        addSpeciesViewModel.observableStatus.observe(viewLifecycleOwner, Observer{
           status -> if(status) {
            //findNavController().navigate(R.id.action_addSpeciesFragment_to_speciesListFragment)
        }
        })


    /*    val speciesListViewModel = ViewModelProvider(this).get(SpeciesListViewModel::class.java)
        speciesListViewModel.observableSpeciesList.observe(viewLifecycleOwner, Observer{
            val totalSpecies = speciesListViewModel.observableSpeciesList.value!!
            totalSpecies.also { fragBinding.progressBar.progress }*/

        }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            val selectedImageUri = result.data!!.data!!
                            addSpeciesViewModel.setImage(selectedImageUri)
                            Picasso.get()
                                .load(selectedImageUri)
                                .into(fragBinding.speciesImage)
                            fragBinding.buttonAddImage.setText(R.string.change_species_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    }


