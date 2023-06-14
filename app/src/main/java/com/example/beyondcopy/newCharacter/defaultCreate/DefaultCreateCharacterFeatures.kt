package com.example.beyondcopy.newCharacter.defaultCreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.beyondcopy.R
import com.example.beyondcopy.adapters.FeaturesListAdapter
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.FeaturesDataBase
import com.example.beyondcopy.databinding.AddNewFeatureDialogBinding
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterFeaturesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class DefaultCreateCharacterFeatures : Fragment() {
    private lateinit var binding: FragmentDefaultCreateCharacterFeaturesBinding
    private lateinit var dataBaseViewModel: DataBaseViewModel
    private lateinit var adapter: FeaturesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultCreateCharacterFeaturesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val characterId = requireArguments().getLong("characterId")

        dataBaseViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]

        val features = dataBaseViewModel.features.value?: emptyArray()

        adapter = FeaturesListAdapter(features, dataBaseViewModel, characterId)
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
                val name = tBinding.newFeatureName.text.toString().trim()
                val des = tBinding.newFeatureDescription.text.toString().trim()

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
        binding.defaultNextToProficienciesBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("characterId", characterId)
            findNavController().navigate(R.id.action_defaultCreateCharacterFeatures_to_defaultCreateCharacterProficiencies, bundle)
        }
    }

    override fun onPause() {
        super.onPause()
        dataBaseViewModel.features.value = adapter.getFeatures()
    }
}