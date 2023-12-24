package ie.setu.habitatappv20.ui.listspecies

import android.app.AlertDialog
import android.os.Bundle
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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.adapters.AddSpeciesAdapter
import ie.setu.habitatappv20.adapters.AddSpeciesClickListener
import ie.setu.habitatappv20.databinding.FragmentSpecieslistBinding
import ie.setu.habitatappv20.helpers.SwipeToDeleteCallback
import ie.setu.habitatappv20.helpers.createLoader
import ie.setu.habitatappv20.helpers.hideLoader
import ie.setu.habitatappv20.helpers.showLoader
import ie.setu.habitatappv20.main.HabitatApp
import ie.setu.habitatappv20.models.AddSpeciesModel

class SpeciesListFragment : Fragment(), AddSpeciesClickListener {

    private lateinit var app: HabitatApp
    private var _fragBinding: FragmentSpecieslistBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var speciesListViewModel: SpeciesListViewModel
    lateinit var loader: AlertDialog

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
        _fragBinding = FragmentSpecieslistBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        loader = createLoader(requireActivity())

        //MVVM pattern
        setupMenu()
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        speciesListViewModel = ViewModelProvider(this).get(SpeciesListViewModel::class.java)
       /* speciesListViewModel.observableSpeciesList.observe(viewLifecycleOwner, Observer
        { speciesList ->
            speciesList?.let { render(speciesList) }
        })*/

        showLoader(loader, "Downloading Species List")
        speciesListViewModel.observableSpeciesList.observe(viewLifecycleOwner,Observer
        {
            speciesList ->
            speciesList?.let {
                render(speciesList as ArrayList<AddSpeciesModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })


        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action =
                SpeciesListFragmentDirections.actionSpeciesListFragmentToAddSpeciesFragment()
            findNavController().navigate(action)
        }

        setSwipeRefresh()

        // requireActivity().title = getString(R.string.action_showSpeciesList)
        //fragBinding.recyclerView.adapter = AddSpeciesAdapter(app.addSpeciesStore.findAll())


        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = fragBinding.recyclerView.adapter as AddSpeciesAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

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
                return NavigationUI.onNavDestinationSelected(
                    menuItem,
                    requireView().findNavController()
                )
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(speciesList: List<AddSpeciesModel>) {
        fragBinding.recyclerView.adapter = AddSpeciesAdapter(speciesList, this)
        if (speciesList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.speciesListNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.speciesListNotFound.visibility = View.GONE
        }
    }

    override fun onAddSpeciesClick(speciesList: AddSpeciesModel) {
        val action =
            SpeciesListFragmentDirections.actionSpeciesListFragmentToSpeciesDetails(specieslistid = speciesList._id)
        findNavController().navigate(action)

    }

    override fun onResume() {
        super.onResume()
        SpeciesListViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader, "Downloading Species List")
            speciesListViewModel.load()

        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }
}