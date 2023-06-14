package com.example.beyondcopy.database

import java.time.LocalDateTime
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val Dao: DataBaseDao
){
    suspend fun createCharacter() = Dao.createCharacter()
    suspend fun getAllCharacters() = Dao.getAllCharacters()
    suspend fun getCharacter(id: Long): CharacterWithAllDataBase = Dao.getCharacter(id)

    suspend fun updateCharactersMain(id: Long, cName: String, cClass: String, cRace: String, cBackground: String, exp: Int, lvl: Int, proficiencyBonus: Int, speed: Int, initiative: Int, armorClass: Int, hitDice: Int, maxHitPoints: Int) =
        Dao.updateCharactersMain(id, cName, cClass, cRace, cBackground, exp, lvl, proficiencyBonus, speed, initiative, armorClass, hitDice, maxHitPoints)
    suspend fun updateStatsSimple(id: Long, stats: List<Int>, proficiencyBonus: Int, perception: Int, skills: List<Boolean>, savingThrows: List<Boolean>) =
        Dao.updateStats(id, stats, proficiencyBonus, perception, skills, savingThrows)
    suspend fun updatePersonality(id: Long, traits: String, ideals: String, bonds: String, flaws: String) =
        Dao.updatePersonality(id, traits, ideals, bonds, flaws)

    suspend fun insertCharacterItem(characterId: Long, name: String, description: String, count: Int): Long =
        Dao.insertCharacterItem(characterId, name, description, count)
    suspend fun deleteCharacterItem(characterId: Long, itemId: Long) =
        Dao.deleteCharacterItem(characterId, itemId)
    suspend fun updateCharacterItem(characterId: Long, itemId: Long, name: String, description: String, count: Int) =
        Dao.updateCharacterItem(characterId, itemId, name, description, count)

    suspend fun insertCharacterWeapon(characterId: Long, name: String, ATKBonus: Int, damage: String, damageType: String, description: String, count: Int): Long =
        Dao.insertCharacterWeapon(characterId, name, ATKBonus, damage, damageType, description, count)
    suspend fun deleteCharacterWeapon(characterId: Long, weaponId: Long) =
        Dao.deleteCharacterWeapon(characterId, weaponId)
    suspend fun updateCharacterWeapon(characterId: Long, weaponId: Long, name: String, ATKBonus: Int, damage: String, damageType: String, description: String, count: Int) =
        Dao.updateCharacterWeapon(characterId, weaponId, name, ATKBonus, damage, damageType, description, count)

    suspend fun insertCharacterFeature(characterId: Long, name: String, description: String): Long =
        Dao.insertCharacterFeature(characterId, name, description)
    suspend fun deleteCharacterFeature(characterId: Long, featureId: Long) =
        Dao.deleteCharacterFeature(characterId, featureId)
    suspend fun updateCharacterFeature(characterId: Long, featureId: Long, name: String, description: String) =
        Dao.updateCharacterFeature(characterId, featureId, name, description)

    suspend fun insertCharacterProficiency(characterId: Long, name: String, description: String): Long =
        Dao.insertCharacterProficiency(characterId, name, description)
    suspend fun deleteCharacterProficiency(characterId: Long, proficiencyId: Long) =
        Dao.deleteCharacterProficiency(characterId, proficiencyId)
    suspend fun updateCharacterProficiency(characterId: Long, proficiencyId: Long, name: String, description: String) =
        Dao.updateCharacterProficiency(characterId, proficiencyId, name, description)
    suspend fun updateLanguages(characterId: Long, languages: String) =
        Dao.updateLanguages(characterId, languages)

    suspend fun insertCharacterNote(characterId: Long, name: String, text: String, priority: Int, dateTime: LocalDateTime, completed: Boolean): Long =
        Dao.insertCharacterNote(characterId, name, text, priority, dateTime, completed)
    suspend fun deleteCharacterNote(characterId: Long, noteId: Long) =
        Dao.deleteCharacterNote(characterId, noteId)
    suspend fun updateCharacterNote(characterId: Long, noteId: Long, name: String, text: String, priority: Int, dateTime: LocalDateTime, completed: Boolean) =
        Dao.updateCharacterNote(characterId, noteId, name, text, priority, dateTime, completed)

    suspend fun deleteAll() = Dao.deleteAll()
}