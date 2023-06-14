package com.example.beyondcopy.csheet.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.beyondcopy.adapters.ProficienciesListAdapter
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.ProficienciesDataBase
import com.example.beyondcopy.databinding.AddNewProficiencyDialogBinding
import com.example.beyondcopy.databinding.FragmentProficienciesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class Proficiencies : Fragment() {
    private lateinit var binding: FragmentProficienciesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProficienciesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dataBaseViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]
        val characterId = requireArguments().getLong("characterId")
        val features = dataBaseViewModel.proficiencies.value?: emptyArray()
        val adapter = ProficienciesListAdapter(features, dataBaseViewModel, characterId)
        binding.proficienciesListLayout.proficienciesRecyclerView.adapter = adapter

        binding.proficienciesListLayout.addProficiency.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val tBinding = AddNewProficiencyDialogBinding.inflate(layoutInflater)

            tBinding.newProficiencyName.addTextChangedListener {
                tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newProficiencyDescription.text.isNotBlank())
            }
            tBinding.newProficiencyDescription.addTextChangedListener {
                tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newProficiencyName.text.isNotBlank())
            }
            tBinding.buttonPad.buttonPadOK.setOnClickListener {
                val name = tBinding.newProficiencyName.text.toString()
                val des = tBinding.newProficiencyDescription.text.toString()

                lifecycleScope.launch {
                    val proficiencyId = dataBaseViewModel.insertCharacterProficiency(characterId, name, des)
                    val proficiency = ProficienciesDataBase(proficiencyId, characterId, name, des)
                    adapter.insertProficiency(proficiency)
                }
                dialog.dismiss()
            }
            tBinding.buttonPad.buttonPadCancel.setOnClickListener{ dialog.dismiss()}
            dialog.setContentView(tBinding.root)
            dialog.show()
        }
    }
}