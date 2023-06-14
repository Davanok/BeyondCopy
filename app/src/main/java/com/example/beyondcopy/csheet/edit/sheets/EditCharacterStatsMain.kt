package com.example.beyondcopy.csheet.edit.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.beyondcopy.R
import com.example.beyondcopy.adapters.HitDicesSpinnerAdapter
import com.example.beyondcopy.classes.Dice
import com.example.beyondcopy.classes.Observer
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterBinding
import com.example.beyondcopy.replace
import com.example.beyondcopy.toInt

class EditCharacterStatsMain: Fragment() {
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
        val characterId = requireArguments().getLong("characterId")
        var stats = requireArguments().getStringArray("stats")!!.map { it.toString() }

        val dataViewModel by activityViewModels<DataBaseViewModel>()

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

        var hitDice = stats[10].toInt()
        binding.hitDice.setOnItemClickListener { _, _, i, _ ->
            hitDice = i
            binding.hitDice.setText(dices[i].dice, false)
        }
        val observer = Observer(binding.defaultNextToStatsBtn,
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
            binding.hitDice,
            binding.maxHitPoints)
        observer.setCompareObserver(stats.replace(10, dices[hitDice].dice)){hitDice != -1}

        binding.CScName.setText(stats[0])
        binding.newCharacterMainParams.editCClass.setText(stats[1])
        binding.newCharacterMainParams.editCRace.setText(stats[2])
        binding.newCharacterMainParams.editCBackground.setText(stats[3])

        binding.editStatsMain.cExperience.setText(stats[4])
        binding.editStatsMain.cLevel.setText(stats[5])
        binding.editStatsMain.cProficiencyBonus.setText(stats[6])

        binding.editParams.cSpeed.setText(stats[7])
        binding.editParams.cInitiative.setText(stats[8])
        binding.editParams.cArmorClass.setText(stats[9])

        binding.hitDice.setText(dices[hitDice].dice, false)
        binding.maxHitPoints.setText(stats[11])

        binding.defaultNextToStatsBtn.text = getString(R.string.apply)
        binding.defaultNextToStatsBtn.setOnClickListener {
            stats = listOf(
                binding.CScName.text.toString(),
                binding.newCharacterMainParams.editCClass.text.toString(),
                binding.newCharacterMainParams.editCRace.text.toString(),
                binding.newCharacterMainParams.editCBackground.text.toString(),

                binding.editStatsMain.cExperience.text.toString(),
                binding.editStatsMain.cLevel.text.toString(),
                binding.editStatsMain.cProficiencyBonus.text.toString(),

                binding.editParams.cSpeed.text.toString(),
                binding.editParams.cInitiative.text.toString(),
                binding.editParams.cArmorClass.text.toString(),

                binding.hitDice.text.toString(),
                binding.maxHitPoints.text.toString()
            )
            observer.setCompareObserver(stats){hitDice != -1}

            it.isEnabled = false
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
        }
    }
}