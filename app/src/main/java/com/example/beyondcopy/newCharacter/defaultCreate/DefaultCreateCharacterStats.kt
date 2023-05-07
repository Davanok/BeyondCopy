package com.example.beyondcopy.newCharacter.defaultCreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterStatsBinding

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

        val dataBaseViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]
        val viewModel: DefaultCreateCharacterViewModel by viewModels()

        val profBonus = arguments?.getInt("proficiencyBonus") ?: 2
        val characterId = arguments?.getLong("characterId") ?: 0L

        binding.editSkillsList.layoutManager = LinearLayoutManager(requireContext())
        binding.editSkillsList.adapter = EditSkillsListAdapter(viewModel, profBonus)

        binding.defaultNextToPersonalityBtn.setOnClickListener {
            val stats = viewModel.stats.value
            val skills = viewModel.skills.value
            val saves = viewModel.saves.value

            fun BooleanArray.toIntArray(): IntArray{
                fun Boolean.toInt(): Int{
                    return if (this) 1
                    else 0
                }
                return this.map { it.toInt() }.toIntArray()
            }
            if(stats != null) {
                val sSkills = skills!!.toIntArray()
                val sSaves = saves!!.toIntArray()

                dataBaseViewModel.updateStatsSimple(characterId, stats, profBonus, 0, sSkills, sSaves)
                val bundle = Bundle()
                bundle.putLong("characterId", characterId)

                findNavController().navigate(R.id.action_defaultCreateCharacterStats_to_defaultCreateCharacterPersonality, bundle)
            }
            else Toast.makeText(requireContext(), R.string.enterParams, Toast.LENGTH_SHORT).show()
        }
    }

}