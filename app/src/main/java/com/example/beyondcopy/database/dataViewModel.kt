package com.example.beyondcopy.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataBaseViewModel @Inject constructor(
    private val repository: DataRepository
): ViewModel(){
    suspend fun getCharacter(id: Long) = repository.getCharacter(id)

    suspend fun getAllCharacters() = repository.getAllCharacters()

    suspend fun insertCharactersMain(cName: String, cClass: String, cRace: String, cBackground: String, exp: Int, lvl: Int, proficiencyBonus: Int, speed: Int, initiative: Int, armorClass: Int) =
        repository.insertCharactersMain(cName, cClass, cRace, cBackground, exp, lvl, proficiencyBonus, speed, initiative, armorClass)

    fun updateStatsSimple(id: Long, stats: IntArray, proficiencyBonus: Int, perception: Int, skills: IntArray, savingThrows: IntArray){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateStatsSimple(id, stats, proficiencyBonus, perception, skills, savingThrows)
        }
    }

    suspend fun getCharacterWithAll(id: Long): CharacterWithAllDataBase = repository.getCharacterWithAll(id)

    fun insertCharacterWeapon(characterId: Long, name: String, ATKBonus: Int, damage: String, damageType: String, description: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCharacterWeapon(characterId, name, ATKBonus, damage, damageType, description)
        }
    }
    fun insertCharacterItem(characterId: Long, name: String, description: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertCharacterItem(characterId, name, description)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun updatePersonality(id: Long, traits: String, ideals: String, bonds: String, flaws: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePersonality(id, traits, ideals, bonds, flaws)
        }
    }
}