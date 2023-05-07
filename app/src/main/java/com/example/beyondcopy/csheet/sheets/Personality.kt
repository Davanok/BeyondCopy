package com.example.beyondcopy.csheet.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beyondcopy.R
import com.example.beyondcopy.databinding.FragmentPersonaityBinding

class Personality : Fragment() {
    private lateinit var binding: FragmentPersonaityBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonaityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val traits = arguments?.getString("traits")?:getString(R.string.notIndicated)
        val ideals = arguments?.getString("ideals")?:getString(R.string.notIndicated)
        val bonds = arguments?.getString("bonds")?:getString(R.string.notIndicated)
        val flaws = arguments?.getString("flaws")?:getString(R.string.notIndicated)

        binding.traits.text = traits
        binding.ideals.text = ideals
        binding.bonds.text = bonds
        binding.flaws.text = flaws
    }
}