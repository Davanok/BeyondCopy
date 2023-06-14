package com.example.beyondcopy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.FeaturesDataBase
import com.example.beyondcopy.databinding.AddNewFeatureDialogBinding
import com.example.beyondcopy.removeAt
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView

class FeaturesListAdapter(private var features: Array<FeaturesDataBase>,
                          private val viewModel: DataBaseViewModel,
                          private val characterId: Long):
    RecyclerView.Adapter<FeaturesListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val card: MaterialCardView = view.findViewById(R.id.featureCard)
        val name: TextView = view.findViewById(R.id.characterFeatureName)
        val description: TextView = view.findViewById(R.id.characterFeatureDescription)
    }
    private lateinit var dialog: BottomSheetDialog
    private lateinit var dialogBinding: AddNewFeatureDialogBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        dialog = BottomSheetDialog(parent.context)
        val inflater = LayoutInflater.from(parent.context)
        dialogBinding = AddNewFeatureDialogBinding.inflate(inflater)

        val view = inflater.inflate(R.layout.feature_card, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = features.size

    private fun dialogShow (position: Int) {
        val tDialog = dialog
        val tBinding = dialogBinding

        tBinding.buttonPad.buttonPadDelete.visibility = View.VISIBLE

        tBinding.newFeatureName.setText(features[position].name)
        tBinding.newFeatureDescription.setText(features[position].description)

        tBinding.newFeatureName.addTextChangedListener {
            tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newFeatureDescription.text.isNotBlank())
        }
        tBinding.newFeatureDescription.addTextChangedListener {
            tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newFeatureName.text.isNotBlank())
        }
        tBinding.buttonPad.buttonPadOK.setOnClickListener {
            val name = tBinding.newFeatureName.text.toString().trim()
            val des = tBinding.newFeatureDescription.text.toString().trim()

            features[position].name = name
            features[position].description = des

            viewModel.updateCharacterFeature(characterId, features[position].featureId, name, des)

            notifyItemChanged(position)
            tDialog.dismiss()
        }
        tBinding.buttonPad.buttonPadDelete.setOnClickListener{
            viewModel.deleteCharacterFeature(characterId, features[position].featureId)
            features = features.removeAt(position)
            notifyItemRemoved(position)
            for(i in position until features.size) notifyItemChanged(i)
            tDialog.dismiss()
        }
        tBinding.buttonPad.buttonPadCancel.setOnClickListener{ tDialog.dismiss()}
        tDialog.setContentView(tBinding.root)
        tDialog.show()
    }
    fun insertFeature(feature: FeaturesDataBase){
        features += feature
        notifyItemInserted(features.size-1)
    }
    fun getFeatures() = features

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = features[position].name
        holder.description.text = features[position].description

        holder.card.setOnClickListener{
            dialogShow(position)
        }
    }
}
