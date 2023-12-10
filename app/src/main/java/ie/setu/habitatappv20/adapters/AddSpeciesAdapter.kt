package ie.setu.habitatappv20.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.databinding.CardAddspeciesBinding
import ie.setu.habitatappv20.models.AddSpeciesModel

class AddSpeciesAdapter constructor(private var addSpecies: List<AddSpeciesModel>)
    : RecyclerView.Adapter<AddSpeciesAdapter.MainHolder>() {

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int):MainHolder {
        val binding = CardAddspeciesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val speciesList = addSpecies[holder.adapterPosition]
        holder.bind(speciesList)
    }


    override fun getItemCount(): Int = addSpecies.size

    inner class MainHolder(val binding: CardAddspeciesBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(speciesList: AddSpeciesModel) {
            binding.amountOfSpeciesSeen.text = speciesList.totalSpecies.toString()
            binding.soilType.text = speciesList.soilType
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)

        }
    }
}