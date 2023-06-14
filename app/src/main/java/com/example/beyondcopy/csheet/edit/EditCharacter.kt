package com.example.beyondcopy.csheet.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentEditCharacterBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class EditCharacter : Fragment() {
    private lateinit var binding: FragmentEditCharacterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditCharacterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val dataViewModel by activityViewModels<DataBaseViewModel>()
        val characterId = requireArguments().getLong("characterId")
        val tabsNames = resources.getStringArray(R.array.tabsNames)

        lifecycleScope.launch {
            val character = dataViewModel.getCharacter(characterId)

            requireActivity().runOnUiThread{

                dataViewModel.items.value = character.items.toTypedArray()
                dataViewModel.weapons.value = character.weapons.toTypedArray()
                dataViewModel.features.value = character.features.toTypedArray()
                dataViewModel.proficiencies.value = character.proficiencies.toTypedArray()

                binding.editCharacterViewPager.adapter = EditCharacterViewPagerAdapter(requireParentFragment(), character.character)

                TabLayoutMediator(binding.editCharacterTabLayout, binding.editCharacterViewPager){ tab, position ->
                    tab.text = tabsNames[position]
                }.attach()
            }
        }

    }
}