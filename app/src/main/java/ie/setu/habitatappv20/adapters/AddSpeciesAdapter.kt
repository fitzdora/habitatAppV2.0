package ie.setu.habitatappv20.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.habitatappv20.R
import ie.setu.habitatappv20.databinding.CardAddspeciesBinding
import ie.setu.habitatappv20.models.AddSpeciesModel

interface AddSpeciesClickListener {
    fun onAddSpeciesClick(speciesList: AddSpeciesModel)
}

class AddSpeciesAdapter constructor(private var speciesList: List<AddSpeciesModel>,
                                    private val listener: AddSpeciesClickListener)
    : RecyclerView.Adapter<AddSpeciesAdapter.MainHolder>() {

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int):MainHolder {
        val binding = CardAddspeciesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val addSpecies = speciesList[holder.adapterPosition]
        holder.bind(addSpecies, listener)
    }


    override fun getItemCount(): Int = speciesList.size

    inner class MainHolder(val binding: CardAddspeciesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(speciesList: AddSpeciesModel, listener: AddSpeciesClickListener) {
            binding.titleText.text = speciesList.commonName
            binding.habitatType.text = speciesList.habitatType
            binding.speciesDescription.text = speciesList.speciesDescription
            binding.amountOfSpeciesSeen.text = speciesList.totalSpecies.toString()
            binding.soilType.text = speciesList.soilType
            binding.speciesImage.setImageResource(R.mipmap.ic_launcher_round)
            //binding with picasso
            Picasso.get().load(speciesList.speciesImage).into(binding.speciesImage)
            binding.addSpecies = speciesList
            binding.root.setOnClickListener { listener.onAddSpeciesClick(speciesList)}
            binding.executePendingBindings()

        }
    }
}