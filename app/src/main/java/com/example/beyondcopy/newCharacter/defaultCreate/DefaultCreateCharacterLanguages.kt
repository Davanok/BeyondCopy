package com.example.beyondcopy.newCharacter.defaultCreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beyondcopy.R
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.FragmentDefaultCreateCharacterLanguagesBinding

class DefaultCreateCharacterLanguages : Fragment() {
    private lateinit var binding: FragmentDefaultCreateCharacterLanguagesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDefaultCreateCharacterLanguagesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dataViewModel by activityViewModels<DataBaseViewModel>()
        val characterId = requireArguments().getLong("characterId")

        binding.languagesList.addTextChangedListener {
            dataViewModel.languages.value = it.toString()
            binding.completeDefaultCreateCharacterBtn.isEnabled = !it.isNullOrBlank()
        }
        binding.completeDefaultCreateCharacterBtn.setOnClickListener {
            dataViewModel.updateLanguages(characterId, binding.languagesList.text!!.toString().trim())
            findNavController().navigate(R.id.action_defaultCreateCharacterLanguages_to_charactersList)
        }
    }
}