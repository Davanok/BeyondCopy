package com.example.beyondcopy.newCharacter.fastCreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beyondcopy.databinding.FragmentFastCreateCharacterBinding

class FastCreateCharacter : Fragment() {
    private lateinit var binding: FragmentFastCreateCharacterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFastCreateCharacterBinding.inflate(layoutInflater)
        return binding.root
    }
}