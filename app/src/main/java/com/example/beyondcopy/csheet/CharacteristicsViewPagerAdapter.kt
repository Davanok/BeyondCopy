package com.example.beyondcopy.csheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beyondcopy.csheet.sheets.Personality
import com.example.beyondcopy.csheet.sheets.Stats
import com.example.beyondcopy.csheet.sheets.skills.Skills
import com.example.beyondcopy.database.CharactersDataBase

class CharacteristicsViewPagerAdapter(
    fragment: Fragment,
    private val character: CharactersDataBase
    ): FragmentStateAdapter(fragment) {

    override fun getItemCount() = 6

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->showStatsFragment(character)
            1->showSkillsFragment(character)
            2->showPersonalityFragment(character)
            else -> showStatsFragment(character)
        }
    }
    private fun showStatsFragment(character: CharactersDataBase): Fragment{
        val proficiencyBonus = character.proficiencyBonus?:2
        val stats = getStats(character)
        val modifiers = stats.map { s -> (s-10)/2 }.toIntArray()
        val perception = if(character.skills != null) character.skills!!.split("|")[11].toInt() * character.proficiencyBonus!!
        else 0

        val saves = getSaves(character, modifiers, proficiencyBonus)

        val arguments = Bundle()
        arguments.putIntArray("stats", stats)
        arguments.putIntArray("modifiers", modifiers)
        arguments.putIntArray("saves", saves)
        arguments.putInt("perception", perception)
        val fragment = Stats()
        fragment.arguments = arguments
        return fragment
    }
    private fun showSkillsFragment(character: CharactersDataBase): Fragment{
        val stats = getStats(character)
        val proficiencyBonus = character.proficiencyBonus?:2
        val modifiers = stats.map { s -> (s-10)/2 }.toIntArray()
        val skillModifiers: BooleanArray =
            if(character.skills!=null) character.skills!!.split("|").map {s -> s == "1"}.toBooleanArray()
            else BooleanArray(18){false}

        val skills = getSkills(skillModifiers, modifiers, proficiencyBonus)

        val arguments = Bundle()
        arguments.putBooleanArray("skillModifiers", skillModifiers)
        arguments.putIntArray("skills", skills)

        val fragment = Skills()
        fragment.arguments = arguments
        return fragment
    }
    private fun showPersonalityFragment(character: CharactersDataBase): Fragment{
        val traits = character.traits
        val ideals = character.ideals
        val bonds = character.bonds
        val flaws = character.flaws

        val arguments = Bundle()
        arguments.putString("traits", traits)
        arguments.putString("ideals", ideals)
        arguments.putString("bonds", bonds)
        arguments.putString("flaws", flaws)

        val fragment = Personality()
        fragment.arguments = arguments

        return fragment
    }

    private fun getSkills(skillsModifiers: BooleanArray, modifiers: IntArray, profBonus: Int): IntArray{
        fun numToSkill(num: Int): Int{
            return when(num){
                0->1
                1->4
                2->3
                3->0
                4->5
                5->3
                6->4
                7->5
                8->3
                9->4
                10->3
                11->4
                12->5
                13->5
                14->3
                15->1
                16->1
                17->4
                else -> -1
            }
        }
        fun Boolean.toInt(): Int{
            return if(this) 1
            else 0
        }
        val skills = IntArray(18){0}
        for(i in skills.indices){
            skills[i] = modifiers[numToSkill(i)] + (skillsModifiers[i].toInt() * profBonus)
        }
        return skills
    }
    private fun getStats(character: CharactersDataBase): IntArray {
        val arr = arrayOf(
            character.strength?:0,
            character.dexterity?:0,
            character.constitution?:0,
            character.intelligence?:0,
            character.wisdom?:0,
            character.charisma?:0
        )
        return arr.toIntArray()
    }
    private fun getSaves(character: CharactersDataBase, modifiers: IntArray, proficiencyBonus: Int): IntArray{
        val saves = IntArray(6)
        val savingThrows: IntArray = if(character.savingThrows!=null) character.savingThrows!!.split("|").map {s->s.toInt()}.toIntArray()
        else IntArray(6) { 0 }

        for(i in saves.indices) {
            saves[i] = modifiers[i] + (savingThrows[i] * proficiencyBonus)
        }
        return saves
    }
}