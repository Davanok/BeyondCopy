package com.example.beyondcopy.csheet.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beyondcopy.databinding.FragmentStatsBinding

class Stats : Fragment() {
    private lateinit var binding: FragmentStatsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val stats = requireArguments().getIntArray("stats")?: IntArray(6){0}
        val modifiers = requireArguments().getIntArray("modifiers")?:IntArray(6){0}
        val saves = requireArguments().getIntArray("saves")?:IntArray(6){0}
        val perception = requireArguments().getInt("perception")

        binding.CSStats.statsStrengthMod.text = modifiers[0].toString()
        binding.CSStats.statsDexterityMod.text = modifiers[1].toString()
        binding.CSStats.statsConstitutionMod.text = modifiers[2].toString()
        binding.CSStats.statsIntelligenceMod.text = modifiers[3].toString()
        binding.CSStats.statsWisdomMod.text = modifiers[4].toString()
        binding.CSStats.statsCharismaMod.text = modifiers[5].toString()

        binding.CSStats.statsStrength.text = stats[0].toString()
        binding.CSStats.statsDexterity.text = stats[1].toString()
        binding.CSStats.statsConstitution.text = stats[2].toString()
        binding.CSStats.statsIntelligence.text = stats[3].toString()
        binding.CSStats.statsWisdom.text = stats[4].toString()
        binding.CSStats.statsCharisma.text = stats[5].toString()

        binding.CSSaves.savesStrength.text = saves[0].toString()
        binding.CSSaves.savesDexterity.text = saves[1].toString()
        binding.CSSaves.savesConstitution.text = saves[2].toString()
        binding.CSSaves.savesIntelligence.text = saves[3].toString()
        binding.CSSaves.savesWisdom.text = saves[4].toString()
        binding.CSSaves.savesCharisma.text = saves[5].toString()

        val passiveWisdom = 10+modifiers[4]+perception
        binding.passiveWisdom.text = passiveWisdom.toString()
    }
}