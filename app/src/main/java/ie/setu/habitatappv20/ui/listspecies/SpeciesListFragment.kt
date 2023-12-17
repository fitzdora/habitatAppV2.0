package ie.setu.habitatappv20.ui.listspecies

import android.os.Bundle
import android.view.*
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.adapters.AddSpeciesAdapter
import ie.setu.habitatappv20.databinding.FragmentSpecieslistBinding
import ie.setu.habitatappv20.main.HabitatApp
import ie.setu.habitatappv20.models.AddSpeciesModel

class SpeciesListFragment : Fragment() {

    private lateinit var app: HabitatApp
    private var _fragBinding: FragmentSpecieslistBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var speciesListViewModel: SpeciesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
       // app = activity?.application as HabitatApp
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragBinding = FragmentSpecieslistBinding.inflate( inflater, container, false)
        val root = fragBinding.root
        //MVVM pattern
        setupMenu()
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        speciesListViewModel = ViewModelProvider(this).get(SpeciesListViewModel::class.java)
        speciesListViewModel.observableSpeciesList.observe(viewLifecycleOwner, Observer
        {
            speciesList ->
            speciesList?.let { render(speciesList) }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = SpeciesListFragmentDirections.actionSpeciesListFragmentToAddSpeciesFragment()
            findNavController().navigate(action)
        }

        //requireActivity().title = getString(R.string.action_showSpeciesList)
        //fragBinding.recyclerView.adapter = AddSpeciesAdapter(app.addSpeciesStore.findAll())
        return root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                //super.onPrepareMenu(menu)
            }
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_specieslist, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return NavigationUI.onNavDestinationSelected(menuItem,
                    requireView().findNavController())
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

   private fun render(speciesList : List<AddSpeciesModel>){
       fragBinding.recyclerView.adapter = AddSpeciesAdapter(speciesList)
       if(speciesList.isEmpty()) {
           fragBinding.recyclerView.visibility = View.GONE
           fragBinding.speciesListNotFound.visibility = View.VISIBLE
       } else {
           fragBinding.recyclerView.visibility = View.VISIBLE
           fragBinding.speciesListNotFound.visibility = View.GONE
       }
   }

    override fun onResume() {
        super.onResume()
        SpeciesListViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}