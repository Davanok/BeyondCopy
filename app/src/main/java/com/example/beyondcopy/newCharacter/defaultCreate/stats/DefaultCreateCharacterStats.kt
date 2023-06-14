package com.example.beyondcopy.newCharacter.defaultCreate.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterStatsBinding
import com.example.beyondcopy.toUnNullableArray

class DefaultCreateCharacterStats : Fragment() {
    private lateinit var binding: FragmentDefaultCreateCharacterStatsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultCreateCharacterStatsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onStart() {
        super.onStart()

        val dataBaseViewModel by activityViewModels<DataBaseViewModel>()
        val viewModel: DefaultCreateStatsViewModel by viewModels()

        val profBonus = arguments?.getInt("proficiencyBonus") ?: 2
        val characterId = arguments?.getLong("characterId") ?: 0L

        binding.editSkillsList.adapter = EditSkillsListAdapter(viewModel, profBonus, binding.defaultNextToPersonalityBtn)

        binding.defaultNextToPersonalityBtn.setOnClickListener {
            val stats = viewModel.stats.value
            val skills = viewModel.skills.value
            val saves = viewModel.saves.value


            if(stats != null) {
                val sSkills = skills!!.toList()
                val sSaves = saves!!.toList()

                dataBaseViewModel.updateStatsSimple(characterId, stats.toUnNullableArray().toList(), profBonus, 0, sSkills, sSaves)
                val bundle = Bundle()
                bundle.putLong("characterId", characterId)

                findNavController().navigate(R.id.action_defaultCreateCharacterStats_to_defaultCreateCharacterPersonality, bundle)
            }
            else Toast.makeText(requireContext(), R.string.enterParams, Toast.LENGTH_SHORT).show()
        }
    }

}