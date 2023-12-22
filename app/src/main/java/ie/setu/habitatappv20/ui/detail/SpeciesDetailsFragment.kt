package ie.setu.habitatappv20.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie.setu.habitatappv20.R

class SpeciesDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = SpeciesDetailsFragment()
    }

    private lateinit var viewModel: SpeciesDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_speciesdetails, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SpeciesDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}