package com.example.beyondcopy.cSheetsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentCharactersListBinding
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
        val dataViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]

        val bottomNavigationMenu = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationMenu.visibility = View.VISIBLE

        binding.newCharacterBtn.setOnClickListener{
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(R.layout.new_character_dialog)


            val fastCreateCharacter = bottomSheetDialog.findViewById<Button>(R.id.fastCreateCharacter)
            val defaultCreateCharacter = bottomSheetDialog.findViewById<Button>(R.id.defaultCreateCharacter)

            fastCreateCharacter?.setOnClickListener{
                findNavController().navigate(R.id.action_charactersList_to_fastCreateCharacter)
                bottomSheetDialog.dismiss()
                bottomNavigationMenu.visibility = View.GONE
            }
            defaultCreateCharacter?.setOnClickListener{
                findNavController().navigate(R.id.action_charactersList_to_defaultCreateCharacter)
                bottomSheetDialog.dismiss()
                bottomNavigationMenu.visibility = View.GONE
            }
            bottomSheetDialog.show()
        }

        binding.charactersList.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch(Dispatchers.IO) {
            val characterCards = dataViewModel.getAllCharacters()

            requireActivity().runOnUiThread {
                binding.charactersList.adapter = CustomAdapter(characterCards)
            }
        }

    }
}