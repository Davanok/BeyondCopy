package com.example.beyondcopy.csheet.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beyondcopy.R
import com.example.beyondcopy.databinding.FragmentMainStatsBinding

class MainStats : Fragment() {
    private lateinit var binding: FragmentMainStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainStatsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val args = requireArguments()
        val characteristics = args.getIntArray("characteristics")?: IntArray(4)
        val crb = args.getStringArray("crb")

        binding.statsMain.proficiencyBonus.text = characteristics[0].toString()
        binding.statsMain.walkingSpeed.text = characteristics[1].toString()
        binding.statsMain.initiative.text = characteristics[2].toString()
        binding.statsMain.armorClass.text = characteristics[3].toString()

        binding.characteristicsMain.CScClass.text = crb?.get(0)?:getString(R.string.cClass)
        binding.characteristicsMain.CScRace.text = crb?.get(1)?:getString(R.string.cRace)
        binding.characteristicsMain.CScBackground.text = crb?.get(2)?:getString(
            R.string.cBackground)
    }
}