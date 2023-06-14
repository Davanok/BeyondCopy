package com.example.beyondcopy.csheet.edit.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterStatsBinding
import com.example.beyondcopy.newCharacter.defaultCreate.stats.DefaultCreateStatsViewModel
import com.example.beyondcopy.newCharacter.defaultCreate.stats.EditSkillsListAdapter
import com.example.beyondcopy.toNullableArray
import com.example.beyondcopy.toUnNullableArray

class EditCharacterSkills: Fragment() {
    private lateinit var binding: FragmentDefaultCreateCharacterStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultCreateCharacterStatsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val viewModel by viewModels<DefaultCreateStatsViewModel>()
        val dataBaseViewModel by activityViewModels<DataBaseViewModel>()

        val arguments = requireArguments()
        val characterId = arguments.getLong("characterId")
        val profBonus = arguments.getInt("proficiencyBonus")
        val perception = arguments.getInt("perception")
        val argStats = arguments.getIntArray("stats")
        val argSaves = arguments.getBooleanArray("saves") ?: BooleanArray(6){false}
        val argSkills = arguments.getBooleanArray("skills") ?: BooleanArray(18){false}
        val modifiers = arguments.getIntArray("modifiers") ?: IntArray(6){0}

        viewModel.stats.value = argStats?.toTypedArray()?.toNullableArray()
        viewModel.saves.value = argSaves
        viewModel.skills.value = argSkills
        viewModel.modifiers.value = modifiers.toTypedArray().toNullableArray()

        binding.defaultNextToPersonalityBtn.text = getString(R.string.apply)
        val adapter = EditSkillsListAdapter(
            viewModel,
            profBonus,
            binding.defaultNextToPersonalityBtn,
            argStats!!.toList(),
            argSaves.toList(),
            argSkills.toList()
        )

        binding.editSkillsList.adapter = adapter
        binding.defaultNextToPersonalityBtn.setOnClickListener {
            it.isEnabled = false

            val stats = viewModel.stats.value!!
            val saves = viewModel.saves.value!!
            val skills = viewModel.skills.value!!

            adapter.updateCompareLists(
                stats.toUnNullableArray().toList(),
                saves.toList(),
                skills.toList()
            )
            val sSkills = skills.toList()
            val sSaves = saves.toList()

            dataBaseViewModel.updateStatsSimple(characterId, stats.toUnNullableArray().toList(), profBonus, perception, sSkills, sSaves)
        }
    }
}