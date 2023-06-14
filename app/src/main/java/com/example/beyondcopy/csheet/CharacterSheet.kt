package com.example.beyondcopy.csheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.beyondcopy.R
import com.example.beyondcopy.classes.ToolbarMenu
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentCharacterSheetBinding
import com.google.android.material.tabs.TabLayoutMediator
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

        val tabsNames = resources.getStringArray(R.array.tabsNames)
        val characterId = requireArguments().getLong("characterId")
        val characterName = requireArguments().getString("characterName")?: getString(R.string.character)
        val settingsTitle = getString(R.string.characterSettingsTitle, characterName)

        val dataViewModel: DataBaseViewModel by activityViewModels()
        dataViewModel.toolbarMenu.value = ToolbarMenu(menuId = R.menu.character_toolbar_menu){
            when(it.itemId){
                R.id.edit_character -> {
                    val action = CharacterSheetDirections.actionCharacterSheetToCharacterSheetSettings(characterId, settingsTitle)
                    findNavController().navigate(action)
                }
            }
            return@ToolbarMenu true
        }


        lifecycleScope.launch(Dispatchers.IO){
            val character = dataViewModel.getCharacter(characterId)

            requireActivity().runOnUiThread{

                dataViewModel.items.value = character.items.toTypedArray()
                dataViewModel.weapons.value = character.weapons.toTypedArray()
                dataViewModel.features.value = character.features.toTypedArray()
                dataViewModel.proficiencies.value = character.proficiencies.toTypedArray()
                dataViewModel.notes.value = character.notes.toTypedArray()

                binding.characterViewPager.adapter = CharacteristicsViewPagerAdapter(requireParentFragment(), character.character)

                TabLayoutMediator(binding.characterTabLayout, binding.characterViewPager){ tab, position ->
                    tab.text = tabsNames[position]
                }.attach()
            }
        }

    }

    override fun onStop() {
        super.onStop()
        val dataViewModel: DataBaseViewModel by activityViewModels()
        dataViewModel.toolbarMenu.value = ToolbarMenu(true)

        dataViewModel.items.value = emptyArray()
        dataViewModel.weapons.value = emptyArray()
        dataViewModel.features.value = emptyArray()
        dataViewModel.proficiencies.value = emptyArray()
        dataViewModel.notes.value = emptyArray()
    }
}