package com.example.beyondcopy.newCharacter.defaultCreate

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterBinding
import kotlinx.coroutines.launch

class DefaultCreateCharacter : Fragment() {
    private lateinit var binding: FragmentDefaultCreateCharacterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultCreateCharacterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dataViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]


        binding.defaultNextToStatsBtn.setOnClickListener{
            val cName = binding.CScName.text
            val cClass = binding.newCharacterMainParams.editCClass.text
            val cRace = binding.newCharacterMainParams.editCRace.text
            val cBackground = binding.newCharacterMainParams.editCBackground.text

            val cExperience = binding.editStatsMain.cExperience.text
            val cLevel = binding.editStatsMain.cLevel.text
            val cProficiencyBonus = binding.editStatsMain.cProficiencyBonus.text

            val cSpeed = binding.editParams.cSpeed.text
            val cInitiative = binding.editParams.cInitiative.text
            val cArmorClass = binding.editParams.cArmorClass.text

            fun Editable.toInt(): Int{
                return this.toString().toInt()
            }
            if(cName.isNotBlank() && cClass.isNotBlank() && cRace.isNotBlank() && cBackground.isNotBlank()
                && cExperience.isNotBlank() && cLevel.isNotBlank() && cProficiencyBonus.isNotBlank()
                && cSpeed.isNotBlank() && cInitiative.isNotBlank() && cArmorClass.isNotBlank()){
                lifecycleScope.launch {
                    val characterId = dataViewModel.insertCharactersMain(
                        cName.toString(),
                        cClass.toString(),
                        cRace.toString(),
                        cBackground.toString(),
                        cExperience.toInt(),
                        cLevel.toInt(),
                        cProficiencyBonus.toInt(),
                        cSpeed.toInt(),
                        cInitiative.toInt(),
                        cArmorClass.toInt()
                    )
                    val arguments = Bundle()
                    arguments.putLong("characterId", characterId)
                    arguments.putInt("proficiencyBonus", cProficiencyBonus.toInt())
                    findNavController().navigate(R.id.action_defaultCreateCharacter_to_defaultCreateCharacterStats, arguments)
                }
            }
            else Toast.makeText(requireContext(), R.string.enterParams, Toast.LENGTH_SHORT).show()
        }
    }
}
