package com.example.beyondcopy.cssettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beyondcopy.databinding.FragmentCharacterListSettingsBinding

class CharacterListSettings : Fragment() {
    private lateinit var binding: FragmentCharacterListSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListSettingsBinding.inflate(layoutInflater)
        return binding.root
    }
}