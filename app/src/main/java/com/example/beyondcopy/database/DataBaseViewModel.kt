package com.example.beyondcopy.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beyondcopy.classes.ToolbarMenu
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class DataBaseViewModel @Inject constructor(
    private val repository: DataRepository
): ViewModel(){
    suspend fun createCharacter() = repository.createCharacter()
    suspend fun getAllCharacters() = repository.getAllCharacters()

    fun updateCharactersMain(id: Long, cName: String, cClass: String, cRace: String, cBackground: String, exp: Int, lvl: Int, proficiencyBonus: Int, speed: Int, initiative: Int, armorClass: Int, hitDice: Int, maxHitPoints: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCharactersMain(id, cName, cClass, cRace, cBackground, exp, lvl, proficiencyBonus, speed, initiative, armorClass, hitDice, maxHitPoints)
        }
    }

    fun updateStatsSimple(id: Long, stats: List<Int>, proficiencyBonus: Int, perception: Int, skills: List<Boolean>, savingThrows: List<Boolean>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateStatsSimple(id, stats, proficiencyBonus, perception, skills, savingThrows)
        }
    }
    suspend fun getCharacter(id: Long): CharacterWithAllDataBase = repository.getCharacter(id)

    val weapons by lazy{
        MutableLiveData<Array<WeaponsDataBase>>()
    }
    suspend fun insertCharacterWeapon(characterId: Long, name: String, ATKBonus: Int, damage: String, damageType: String, description: String, count: Int): Long =
        repository.insertCharacterWeapon(characterId, name, ATKBonus, damage, damageType, description, count)
    fun deleteCharacterWeapon(characterId: Long, weaponId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCharacterWeapon(characterId, weaponId)
        }
    }
    fun updateCharacterWeapon(characterId: Long, weaponId: Long, name: String, ATKBonus: Int, damage: String, damageType: String, description: String, count: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCharacterWeapon(characterId, weaponId, name, ATKBonus, damage, damageType, description, count)
        }
    }

    val items by lazy{
        MutableLiveData<Array<ItemsDataBase>>()
    }
    suspend fun insertCharacterItem(characterId: Long, name: String, description: String, count: Int): Long =
        repository.insertCharacterItem(characterId, name, description, count)
    fun deleteCharacterItem(characterId: Long, itemId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCharacterItem(characterId, itemId)
        }
    }
    fun updateCharacterItem(characterId: Long, itemId: Long, name: String, description: String, count: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCharacterItem(characterId, itemId, name, description, count)
        }
    }

    val features by lazy{
        MutableLiveData<Array<FeaturesDataBase>>()
    }
    suspend fun insertCharacterFeature(characterId: Long, name: String, description: String): Long =
        repository.insertCharacterFeature(characterId, name, description)
    fun deleteCharacterFeature(characterId: Long, featureId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCharacterFeature(characterId, featureId)
        }
    }
    fun updateCharacterFeature(characterId: Long, featureId: Long, name: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCharacterFeature(characterId, featureId, name, description)
        }
    }


    val proficiencies by lazy{
        MutableLiveData<Array<ProficienciesDataBase>>()
    }
    suspend fun insertCharacterProficiency(characterId: Long, name: String, description: String): Long =
        repository.insertCharacterProficiency(characterId, name, description)
    fun deleteCharacterProficiency(characterId: Long, featureId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCharacterProficiency(characterId, featureId)
        }
    }
    fun updateCharacterProficiency(characterId: Long, featureId: Long, name: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCharacterProficiency(characterId, featureId, name, description)
        }
    }


    val notes by lazy{
        MutableLiveData<Array<NotesDataBase>>()
    }
    suspend fun insertCharacterNote(characterId: Long, name: String, text: String, priority: Int, dateTime: LocalDateTime, completed: Boolean): Long =
        repository.insertCharacterNote(characterId, name, text, priority, dateTime, completed)
    fun deleteCharacterNote(characterId: Long, noteId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCharacterNote(characterId, noteId)
        }
    }
    fun updateCharacterNote(characterId: Long, noteId: Long, name: String, text: String, priority: Int, dateTime: LocalDateTime, completed: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCharacterNote(characterId, noteId, name, text, priority, dateTime, completed)
        }
    }

    val languages by lazy{
        MutableLiveData<String>()
    }
    fun updateLanguages(characterId: Long, languages: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLanguages(characterId, languages)
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

    val toolbarMenu by lazy {
        MutableLiveData<ToolbarMenu>()
    }
}