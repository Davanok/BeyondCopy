package com.example.beyondcopy.csheet.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beyondcopy.csheet.edit.sheets.EditCharacterPersonality
import com.example.beyondcopy.csheet.edit.sheets.EditCharacterSkills
import com.example.beyondcopy.csheet.edit.sheets.EditCharacterStatsMain
import com.example.beyondcopy.database.CharactersDataBase

class EditCharacterViewPagerAdapter(
    parentFragment: Fragment,
    private val character: CharactersDataBase
): FragmentStateAdapter(parentFragment) {
    override fun getItemCount() = 9

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> editMainCharacteristics(character)
            1 -> editStats(character)
            2 -> editPersonality(character)
            else -> editMainCharacteristics(character)
        }
    }

    private fun editMainCharacteristics(character: CharactersDataBase): Fragment{
        val stats = arrayOf(
                character.cName ?: "",
                character.cClass ?: "",
                character.cRace ?: "",
                character.cBackground ?: "",
                character.exp?.toString() ?: "0",
                character.lvl?.toString() ?: "0",
                character.proficiencyBonus?.toString() ?: "0",
                character.speed?.toString() ?: "0",
                character.initiative?.toString() ?: "0",
                character.armorClass?.toString() ?: "0",
                character.hitDice?.toString() ?: "0",
                character.maxHitPoints?.toString() ?: "0"
            )

        val bundle = Bundle()
        bundle.putLong("characterId", character.id)
        bundle.putStringArray("stats", stats)

        val fragment = EditCharacterStatsMain()
        fragment.arguments = bundle
        return fragment
    }
    private fun editStats(character: CharactersDataBase): Fragment{
        val stats = character.stats?.toIntArray() ?: IntArray(6){10}
        val saves = character.savingThrows?.toBooleanArray() ?: BooleanArray(6){false}
        val skills = character.skills?.toBooleanArray() ?: BooleanArray(18){false}

        val bundle = Bundle()
        bundle.putLong("characterId", character.id)
        bundle.putInt("proficiencyBonus", character.proficiencyBonus ?: 2)
        bundle.putInt("perception", character.perception ?: 0)
        bundle.putIntArray("stats", stats)
        bundle.putBooleanArray("saves", saves)
        bundle.putBooleanArray("skills", skills)
        bundle.putIntArray("modifiers", stats.map { s -> (s-10)/2 }.toIntArray())

        val fragment = EditCharacterSkills()
        fragment.arguments = bundle
        return fragment
    }
    private fun editPersonality(character: CharactersDataBase): Fragment{
        val bundle = Bundle()
        bundle.putLong("characterId", character.id)
        bundle.putString("traits", character.traits)
        bundle.putString("ideals", character.ideals)
        bundle.putString("bonds", character.bonds)
        bundle.putString("flaws", character.flaws)

        val fragment = EditCharacterPersonality()
        fragment.arguments = bundle
        return fragment
    }

}