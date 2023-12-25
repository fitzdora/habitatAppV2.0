package ie.setu.habitatappv20.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ie.setu.habitatappv20.databinding.FragmentSpeciesdetailsBinding
import timber.log.Timber

class SpeciesDetailsFragment : Fragment() {


    private lateinit var detailViewModel: SpeciesDetailsViewModel
    private val args: SpeciesDetailsFragmentArgs by navArgs()
    private var _fragBinding: FragmentSpeciesdetailsBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val speciesListId = args.specieslistid
        _fragBinding = FragmentSpeciesdetailsBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        Toast.makeText(context, "Species ID Selected : ${args.specieslistid}", Toast.LENGTH_LONG).show()

        detailViewModel = ViewModelProvider(this).get(SpeciesDetailsViewModel::class.java)
        detailViewModel.observableSpecies.observe(viewLifecycleOwner, Observer { render () })

        val action = SpeciesDetailsFragmentDirections.actionSpeciesDetailsToSpeciesListFragment()
        findNavController().navigate(action)

        return root
    }

    private fun render() {
        fragBinding.editCommonName.setText("Edit CommonName")
        fragBinding.editScientificName.setText("Edit ScientificName")
        fragBinding.editSpeciesDescription.setText("Edit Description")
        fragBinding.editNoOfSpeciesSeen.setText("0")
        Timber.i("Edit Species Details working")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getSpecies(args.specieslistid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}