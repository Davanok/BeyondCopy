package com.example.beyondcopy.newCharacter.defaultCreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beyondcopy.R
import com.example.beyondcopy.adapters.HitDicesSpinnerAdapter
import com.example.beyondcopy.classes.Dice
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterBinding
import com.example.beyondcopy.setObserver
import com.example.beyondcopy.toInt

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
        val dataViewModel by activityViewModels<DataBaseViewModel>()

        val characterId = requireArguments().getLong("characterId")

        val dices = listOf(
            Dice(requireContext(), 4),
            Dice(requireContext(), 6),
            Dice(requireContext(), 8),
            Dice(requireContext(), 10),
            Dice(requireContext(), 12),
            Dice(requireContext(), 20)
        )
        val adapter = HitDicesSpinnerAdapter(requireContext(), dices)
        binding.hitDice.setAdapter(adapter)

        var hitDice = -1
        binding.hitDice.setText("", false)
        binding.hitDice.setOnItemClickListener { _, _, i, _ ->
            hitDice = i
            binding.hitDice.setText(dices[i].dice, false)
        }
        setObserver(binding.defaultNextToStatsBtn,

            binding.CScName,
            binding.newCharacterMainParams.editCClass,
            binding.newCharacterMainParams.editCRace,
            binding.newCharacterMainParams.editCBackground,
            binding.editStatsMain.cExperience,
            binding.editStatsMain.cLevel,
            binding.editStatsMain.cProficiencyBonus,
            binding.editParams.cSpeed,
            binding.editParams.cArmorClass,
            binding.editParams.cInitiative,
            binding.maxHitPoints,
            binding.hitDice){
            hitDice != -1
        }

        binding.defaultNextToStatsBtn.setOnClickListener{
            dataViewModel.updateCharactersMain(
                characterId,
                binding.CScName.text.toString(),
                binding.newCharacterMainParams.editCClass.text.toString(),
                binding.newCharacterMainParams.editCRace.text.toString(),
                binding.newCharacterMainParams.editCBackground.text.toString(),
                binding.editStatsMain.cExperience.text.toInt(),
                binding.editStatsMain.cLevel.text.toInt(),
                binding.editStatsMain.cProficiencyBonus.text.toInt(),
                binding.editParams.cSpeed.text.toInt(),
                binding.editParams.cArmorClass.text.toInt(),
                binding.editParams.cInitiative.text.toInt(),
                hitDice,
                binding.maxHitPoints.text.toInt()
            )
            val arguments = Bundle()
            arguments.putLong("characterId", characterId)
            arguments.putInt("proficiencyBonus", binding.editStatsMain.cProficiencyBonus.text.toInt())
            findNavController().navigate(R.id.action_defaultCreateCharacter_to_defaultCreateCharacterStats, arguments)
        }
    }
}
