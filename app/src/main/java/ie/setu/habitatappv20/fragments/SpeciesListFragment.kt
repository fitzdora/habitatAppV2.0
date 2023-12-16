package ie.setu.habitatappv20.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.adapters.AddSpeciesAdapter
import ie.setu.habitatappv20.databinding.FragmentSpecieslistBinding
import ie.setu.habitatappv20.main.HabitatApp

class SpeciesListFragment : Fragment() {

    private lateinit var app: HabitatApp
    private var _fragBinding: FragmentSpecieslistBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        app = activity?.application as HabitatApp
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_specieslist, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) ||
        super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragBinding = FragmentSpecieslistBinding.inflate( inflater, container, false)
        val root = fragBinding.root
        requireActivity().title = getString(R.string.action_showSpeciesList)

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        fragBinding.recyclerView.adapter = AddSpeciesAdapter(app.addSpeciesStore.findAll())

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SpeciesListFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}