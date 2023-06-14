package com.example.beyondcopy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.ProficienciesDataBase
import com.example.beyondcopy.databinding.AddNewProficiencyDialogBinding
import com.example.beyondcopy.removeAt
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView

class ProficienciesListAdapter(
    private var proficiencies: Array<ProficienciesDataBase>,
    private val viewModel: DataBaseViewModel,
    private val characterId: Long
):
    RecyclerView.Adapter<ProficienciesListAdapter.ViewHolder>(){
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val card: MaterialCardView = view.findViewById(R.id.proficiencyCard)
        val name: TextView = view.findViewById(R.id.characterProficiencyName)
        val description: TextView = view.findViewById(R.id.characterProficiencyDescription)
    }
    private lateinit var dialog: BottomSheetDialog
    private lateinit var dialogBinding: AddNewProficiencyDialogBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        dialog = BottomSheetDialog(parent.context)
        dialogBinding = AddNewProficiencyDialogBinding.inflate(inflater)

        val view = inflater.inflate(R.layout.proficiencies_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = proficiencies.size


    private fun dialogShow (position: Int) {
        val tDialog = dialog
        val tBinding = dialogBinding

        tBinding.buttonPad.buttonPadDelete.visibility = View.VISIBLE

        tBinding.newProficiencyName.setText(proficiencies[position].name)
        tBinding.newProficiencyDescription.setText(proficiencies[position].description)

        tBinding.newProficiencyName.addTextChangedListener {
            tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newProficiencyDescription.text.isNotBlank())
        }
        tBinding.newProficiencyDescription.addTextChangedListener {
            tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newProficiencyName.text.isNotBlank())
        }
        tBinding.buttonPad.buttonPadOK.setOnClickListener {
            val name = tBinding.newProficiencyName.text.toString().trim()
            val des = tBinding.newProficiencyDescription.text.toString().trim()

            proficiencies[position].name = name
            proficiencies[position].description = des

            viewModel.updateCharacterProficiency(characterId, proficiencies[position].proficiencyId, name, des)

            notifyItemChanged(position)
            tDialog.dismiss()
        }
        tBinding.buttonPad.buttonPadDelete.setOnClickListener{
            viewModel.deleteCharacterProficiency(characterId, proficiencies[position].proficiencyId)
            proficiencies = proficiencies.removeAt(position)
            notifyItemRemoved(position)
            for(i in position until proficiencies.size) notifyItemChanged(i)
            tDialog.dismiss()
        }
        tBinding.buttonPad.buttonPadCancel.setOnClickListener{ tDialog.dismiss()}
        tDialog.setContentView(tBinding.root)
        tDialog.show()
    }
    fun insertProficiency(proficiency: ProficienciesDataBase){
        proficiencies += proficiency
        notifyItemInserted(proficiencies.size-1)
    }
    fun getProficiencies() = proficiencies

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = proficiencies[position].name
        holder.description.text = proficiencies[position].description

        holder.card.setOnClickListener{
            dialogShow(position)
        }
    }
}