package com.example.beyondcopy.newCharacter.defaultCreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterPersonalityBinding
import com.example.beyondcopy.setObserver

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
        val databaseViewModel by activityViewModels<DataBaseViewModel>()
        val characterId = requireArguments().getLong("characterId")
        setObserver(binding.defaultNextToItemsBtn,
            binding.personalityTraits,
            binding.personalityIdeals,
            binding.personalityBonds,
            binding.personalityFlaws)
        binding.defaultNextToItemsBtn.setOnClickListener {
            val traits = binding.personalityTraits.text.toString().trim()
            val ideals = binding.personalityIdeals.text.toString().trim()
            val bonds = binding.personalityBonds.text.toString().trim()
            val flaws = binding.personalityFlaws.text.toString().trim()

            databaseViewModel.updatePersonality(characterId, traits, ideals, bonds, flaws)
            val bundle = Bundle()
            bundle.putLong("characterId", characterId)
            findNavController().navigate(R.id.action_defaultCreateCharacterPersonality_to_defaultCreateCharacterItems, bundle)
        }
    }
}