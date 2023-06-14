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
import com.example.beyondcopy.adapters.ProficienciesListAdapter
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.database.ProficienciesDataBase
import com.example.beyondcopy.databinding.AddNewProficiencyDialogBinding
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterProficienciesBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class DefaultCreateCharacterProficiencies : Fragment() {
    private lateinit var binding: FragmentDefaultCreateCharacterProficienciesBinding
    private lateinit var dataBaseViewModel: DataBaseViewModel
    private lateinit var adapter: ProficienciesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultCreateCharacterProficienciesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val characterId = requireArguments().getLong("characterId")

        dataBaseViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]

        val proficiencies = dataBaseViewModel.proficiencies.value?: emptyArray()

        adapter = ProficienciesListAdapter(proficiencies, dataBaseViewModel, characterId)
        binding.proficienciesList.proficienciesRecyclerView.adapter = adapter

        binding.proficienciesList.addProficiency.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val tBinding = AddNewProficiencyDialogBinding.inflate(layoutInflater)

            tBinding.newProficiencyName.addTextChangedListener {
                tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newProficiencyDescription.text.isNotBlank())
            }
            tBinding.newProficiencyDescription.addTextChangedListener {
                tBinding.buttonPad.buttonPadOK.isEnabled = (it!!.isNotBlank() && tBinding.newProficiencyName.text.isNotBlank())
            }
            tBinding.buttonPad.buttonPadOK.setOnClickListener {
                val name = tBinding.newProficiencyName.text.toString().trim()
                val des = tBinding.newProficiencyDescription.text.toString().trim()

                lifecycleScope.launch {
                    val proficiencyId = dataBaseViewModel.insertCharacterProficiency(characterId, name, des)
                    val feature = ProficienciesDataBase(proficiencyId, characterId, name, des)
                    adapter.insertProficiency(feature)
                }
                dialog.dismiss()
            }
            tBinding.buttonPad.buttonPadCancel.setOnClickListener{ dialog.dismiss()}
            dialog.setContentView(tBinding.root)
            dialog.show()
        }
        binding.defaultNextToLanguagesBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("characterId", characterId)
            findNavController().navigate(R.id.action_defaultCreateCharacterProficiencies_to_defaultCreateCharacterLanguages, bundle)
        }
    }

    override fun onPause() {
        super.onPause()
        dataBaseViewModel.proficiencies.value = adapter.getProficiencies()
    }
}