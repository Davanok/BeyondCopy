package com.example.beyondcopy.csheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beyondcopy.csheet.sheets.Features
import com.example.beyondcopy.csheet.sheets.Items
import com.example.beyondcopy.csheet.sheets.Languages
import com.example.beyondcopy.csheet.sheets.MainStats
import com.example.beyondcopy.csheet.sheets.Notes
import com.example.beyondcopy.csheet.sheets.Personality
import com.example.beyondcopy.csheet.sheets.Proficiencies
import com.example.beyondcopy.csheet.sheets.Stats
import com.example.beyondcopy.csheet.sheets.Weapons
import com.example.beyondcopy.csheet.sheets.skills.Skills
import com.example.beyondcopy.database.CharactersDataBase
import com.example.beyondcopy.toInt

class CharacteristicsViewPagerAdapter(
    parentFragment: Fragment,
    private val character: CharactersDataBase
    ): FragmentStateAdapter(parentFragment) {
    override fun getItemCount() = 10

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->showMainStatsFragment(character)
            1->showStatsFragment(character)
            2->showSkillsFragment(character)
            3->showPersonalityFragment(character)
            4->showItemsFragment(character.id)
            5->showWeaponsFragment(character.id)
            6->showFeaturesFragment(character.id)
            7->showProficienciesFragment(character.id)
            8->showLanguagesFragment(character.languages)
            9->showNotesFragment(character.id)
            else -> showMainStatsFragment(character)
        }
    }
    private fun showMainStatsFragment(character: CharactersDataBase): Fragment{

        val characteristics = arrayOf(
                character.proficiencyBonus?:0, character.speed?:0, character.initiative?:0, character.armorClass?:0
        ).toIntArray()

        val crb = arrayOf(
            character.cClass, character.cRace, character.cBackground
        )

        val bundle = Bundle()
        bundle.putIntArray("characteristics", characteristics)
        bundle.putStringArray("crb", crb)

        val fragment = MainStats()
        fragment.arguments = bundle
        return fragment
    }
    private fun showStatsFragment(character: CharactersDataBase): Fragment{
        val proficiencyBonus = character.proficiencyBonus?:2
        val stats = character.stats?:List(6){0}
        val modifiers = stats.map { s -> (s-10)/2 }.toIntArray()
        val perception = if(character.skills != null) character.skills!![11].toInt() * character.proficiencyBonus!!
        else 0

        val saves = getSaves(character, modifiers, proficiencyBonus)

        val arguments = Bundle()
        arguments.putIntArray("stats", stats.toIntArray())
        arguments.putIntArray("modifiers", modifiers)
        arguments.putIntArray("saves", saves)
        arguments.putInt("perception", perception)
        val fragment = Stats()
        fragment.arguments = arguments
        return fragment
    }
    private fun showSkillsFragment(character: CharactersDataBase): Fragment{
        val stats = character.stats?:List(6){0}
        val proficiencyBonus = character.proficiencyBonus?:2
        val modifiers = stats.map { s -> (s-10)/2 }.toIntArray()
        val skillModifiers = character.skills ?: List(18) { false }

        val skills = getSkills(skillModifiers, modifiers, proficiencyBonus)

        val arguments = Bundle()
        arguments.putBooleanArray("skillModifiers", skillModifiers.toBooleanArray())
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
    private fun showWeaponsFragment(characterId: Long): Fragment{

        val arguments = Bundle()
        arguments.putLong("characterId", characterId)
        val fragment = Weapons()
        fragment.arguments = arguments

        return fragment
    }
    private fun showItemsFragment(characterId: Long): Fragment{
        val arguments = Bundle()
        arguments.putLong("characterId", characterId)
        val fragment = Items()
        fragment.arguments = arguments

        return fragment
    }
    private fun showFeaturesFragment(characterId: Long): Fragment{
        val arguments = Bundle()
        arguments.putLong("characterId", characterId)
        val fragment = Features()
        fragment.arguments = arguments

        return fragment
    }
    private fun showProficienciesFragment(characterId: Long): Fragment{
        val arguments = Bundle()
        arguments.putLong("characterId", characterId)
        val fragment = Proficiencies()
        fragment.arguments = arguments

        return fragment
    }
    private fun showLanguagesFragment(languages: String?): Fragment{
        val arguments = Bundle()
        arguments.putString("languages", languages)
        val fragment = Languages()
        fragment.arguments = arguments

        return fragment
    }
    private fun showNotesFragment(characterId: Long): Fragment{
        val arguments = Bundle()
        arguments.putLong("characterId", characterId)
        val fragment = Notes()
        fragment.arguments = arguments
        return fragment
    }


    private fun getSkills(skillsModifiers: List<Boolean>, modifiers: IntArray, profBonus: Int): IntArray{
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
        val skills = IntArray(18){0}
        for(i in skills.indices){
            skills[i] = modifiers[numToSkill(i)] + (skillsModifiers[i].toInt() * profBonus)
        }
        return skills
    }
    private fun getSaves(character: CharactersDataBase, modifiers: IntArray, proficiencyBonus: Int): IntArray{
        val saves = IntArray(6)
        val savingThrows: List<Boolean> = if(character.savingThrows!=null) character.savingThrows!!
        else List(6){false}

        for(i in saves.indices) {
            saves[i] = modifiers[i] + (savingThrows[i].toInt() * proficiencyBonus)
        }
        return saves
    }
}