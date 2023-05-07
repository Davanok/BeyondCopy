package com.example.beyondcopy.csheet.sheets.skills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beyondcopy.R
import com.example.beyondcopy.databinding.FragmentSkillsBinding

class Skills : Fragment() {
    private lateinit var binding: FragmentSkillsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSkillsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val skills = requireArguments().getIntArray("skills")?: IntArray(18){0}
        val skillModifiers = requireArguments().getBooleanArray("skillModifiers")?: BooleanArray(18){false}
        val skillNames = arrayOf(
            getString(R.string.acrobatics),
            getString(R.string.animalHandling),
            getString(R.string.arcana),
            getString(R.string.athletics),
            getString(R.string.deception),
            getString(R.string.history),
            getString(R.string.insight),
            getString(R.string.intimidation),
            getString(R.string.investigation),
            getString(R.string.medicine),
            getString(R.string.nature),
            getString(R.string.perception),
            getString(R.string.performance),
            getString(R.string.persuasion),
            getString(R.string.religion),
            getString(R.string.sleightOfHand),
            getString(R.string.stealth),
            getString(R.string.survival)
        )
        val modifiersNames = arrayOf(
            getString(R.string.strengthShort),
            getString(R.string.dexterityShort),
            getString(R.string.constitutionShort),
            getString(R.string.intelligenceShort),
            getString(R.string.wisdomShort),
            getString(R.string.charismaShort)
        )

        binding.skillsList.layoutManager = LinearLayoutManager(requireContext())
        binding.skillsList.adapter = SkillsListAdapter(skills, skillModifiers, skillNames, modifiersNames)
    }
}