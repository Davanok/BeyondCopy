package com.example.beyondcopy.csheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentCharacterSheetBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterSheet : Fragment() {
    private lateinit var binding: FragmentCharacterSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dataViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]

        val characterId = requireArguments().getLong("id")
        lifecycleScope.launch(Dispatchers.IO){
            val character = dataViewModel.getCharacter(characterId)

            requireActivity().runOnUiThread{
                binding.CScName.text = character.cName?:getString(R.string.cName)

                binding.statsMain.proficiencyBonus.text = (character.proficiencyBonus?:0).toString()
                binding.statsMain.walkingSpeed.text = (character.speed?:0).toString()
                binding.statsMain.initiative.text = (character.initiative?:0).toString()
                binding.statsMain.armorClass.text = (character.armorClass?:0).toString()

                binding.characteristicsMain.CScClass.text = character.cClass?:getString(R.string.cClass)
                binding.characteristicsMain.CScRace.text = character.cRace?:getString(R.string.cRace)
                binding.characteristicsMain.CScBackground.text = character.cBackground?:getString(R.string.cBackground)

                binding.characteristicsViewPager.adapter = CharacteristicsViewPagerAdapter(requireParentFragment(), character)
            }
        }

    }
}