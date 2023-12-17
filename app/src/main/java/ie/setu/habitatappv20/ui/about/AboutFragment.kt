package ie.setu.habitatappv20.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.databinding.FragmentSpecieslistBinding
import ie.setu.habitatappv20.main.HabitatApp


class AboutFragment : Fragment() {
    private lateinit var app: HabitatApp
    private var _fragBinding: FragmentSpecieslistBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        app = activity?.application as HabitatApp
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AboutFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}