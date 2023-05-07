package com.example.beyondcopy.newCharacter.defaultCreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterPersonalityBinding

class DefaultCreateCharacterPersonality : Fragment() {
    private lateinit var binding: FragmentDefaultCreateCharacterPersonalityBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultCreateCharacterPersonalityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val databaseViewModel = ViewModelProvider(requireActivity())[DataBaseViewModel::class.java]
        val characterId = requireArguments().getLong("characterId")
        binding.defaultNextToItemsBtn.setOnClickListener {
            val traits = binding.personalityTraits.text
            val ideals = binding.personalityIdeals.text
            val bonds = binding.personalityBonds.text
            val flaws = binding.personalityFlaws.text
            if(traits.isNotBlank() && ideals.isNotBlank() && bonds.isNotBlank() && flaws.isNotBlank()){
                databaseViewModel.updatePersonality(characterId, traits.toString(), ideals.toString(), bonds.toString(), flaws.toString())
                val bundle = Bundle()
                bundle.putLong("characterId", characterId)
                findNavController().navigate(R.id.action_defaultCreateCharacterPersonality_to_defaultCreateCharacterItems, bundle)
            }
            else Toast.makeText(requireContext(), R.string.enterParams, Toast.LENGTH_SHORT).show()
        }
    }
}