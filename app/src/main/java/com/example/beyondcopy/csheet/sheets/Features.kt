package com.example.beyondcopy.csheet.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.beyondcopy.adapters.FeaturesListAdapter
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.FeaturesDataBase
import com.example.beyondcopy.databinding.AddNewFeatureDialogBinding
import com.example.beyondcopy.databinding.FragmentFeaturesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class Features : Fragment() {
    private lateinit var binding: FragmentFeaturesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeaturesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dataBaseViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]
        val characterId = requireArguments().getLong("characterId")
        val features = dataBaseViewModel.features.value?: emptyArray()
        val adapter = FeaturesListAdapter(features, dataBaseViewModel, characterId)
        binding.featuresList.featuresRecycleView.adapter = adapter

        binding.featuresList.addFeature.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val tBinding = AddNewFeatureDialogBinding.inflate(layoutInflater)

            tBinding.newFeatureName.addTextChangedListener {
                tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newFeatureDescription.text.isNotBlank())
            }
            tBinding.newFeatureDescription.addTextChangedListener {
                tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newFeatureName.text.isNotBlank())
            }
            tBinding.buttonPad.buttonPadOK.setOnClickListener {
                val name = tBinding.newFeatureName.text.toString()
                val des = tBinding.newFeatureDescription.text.toString()

                lifecycleScope.launch {
                    val featureId = dataBaseViewModel.insertCharacterFeature(characterId, name, des)
                    val feature = FeaturesDataBase(featureId, characterId, name, des)
                    adapter.insertFeature(feature)
                }
                dialog.dismiss()
            }
            tBinding.buttonPad.buttonPadCancel.setOnClickListener{ dialog.dismiss()}
            dialog.setContentView(tBinding.root)
            dialog.show()
        }
    }
}