package com.example.beyondcopy.charactersList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentCharactersListBinding
import com.example.beyondcopy.databinding.NewCharacterDialogBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersList : Fragment() {
    private lateinit var binding: FragmentCharactersListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dataViewModel: DataBaseViewModel by activityViewModels()

        val bottomNavigationMenu = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationMenu.visibility = View.VISIBLE

        binding.newCharacterBtn.setOnClickListener{
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val dBinding = NewCharacterDialogBinding.inflate(layoutInflater)

            fun createCharacter(default: Boolean){
                bottomSheetDialog.dismiss()
                bottomNavigationMenu.visibility = View.GONE

                dataViewModel.items.value = emptyArray()
                dataViewModel.weapons.value = emptyArray()
                dataViewModel.features.value = emptyArray()
                dataViewModel.proficiencies.value = emptyArray()
                dataViewModel.notes.value = emptyArray()
                dataViewModel.languages.value = ""

                lifecycleScope.launch {
                    val characterId = dataViewModel.createCharacter()

                    val bundle = Bundle()
                    bundle.putLong("characterId", characterId)

                    findNavController().navigate(
                        if(default) R.id.action_charactersList_to_defaultCreateCharacter
                        else R.id.action_charactersList_to_fastCreateCharacter,
                        bundle
                    )
                }
            }

            dBinding.fastCreateCharacter.setOnClickListener{createCharacter(false)}
            dBinding.defaultCreateCharacter.setOnClickListener{createCharacter(true)}

            bottomSheetDialog.setContentView(dBinding.root)
            bottomSheetDialog.show()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val characterCards = dataViewModel.getAllCharacters()

            requireActivity().runOnUiThread {
                binding.charactersList.adapter = CharactersListAdapter(characterCards.reversed(), bottomNavigationMenu)
            }
        }
    }
}