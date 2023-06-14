package com.example.beyondcopy.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import java.time.LocalDateTime


@Dao
interface DataBaseDao{
    @Query("DELETE FROM characters")
    suspend fun deleteAll()

    @Query("INSERT INTO characters (cName) VALUES (:cName)")
    suspend fun createCharacter(cName: String = "default name"): Long

    @Query("UPDATE characters SET cName = :cName, cClass = :cClass, cRace = :cRace, cBackground = :cBackground, exp = :exp, lvl = :lvl, proficiencyBonus = :proficiencyBonus, speed = :speed, initiative = :initiative, armorClass = :armorClass, hitDice = :hitDice, maxHitPoints = :maxHitPoints WHERE id LIKE :id")
    suspend fun updateCharactersMain(id: Long, cName: String, cClass: String, cRace: String, cBackground: String, exp: Int, lvl: Int, proficiencyBonus: Int, speed: Int, initiative: Int, armorClass: Int, hitDice: Int, maxHitPoints: Int)

    @Query("UPDATE characters SET stats = :stats, proficiencyBonus = :proficiencyBonus, perception = :perception, skills = :skills, savingThrows = :savingThrows WHERE id LIKE :id")
    suspend fun updateStats(id: Long, stats: List<Int>, proficiencyBonus: Int, perception: Int, skills: List<Boolean>, savingThrows: List<Boolean>)

    @Query("UPDATE characters SET traits = :traits, ideals = :ideals, bonds = :bonds, flaws = :flaws WHERE id LIKE :id")
    suspend fun updatePersonality(id: Long, traits: String, ideals: String, bonds: String, flaws: String)

    @Transaction
    @Query("SELECT * FROM characters WHERE id LIKE :id LIMIT 1")
    suspend fun getCharacter(id: Long): CharacterWithAllDataBase

    @Query("INSERT INTO weapons (characterId, name, ATKBonus, damage, damageType, description, count) VALUES (:characterId, :name, :ATKBonus, :damage, :damageType, :description, :count)")
    suspend fun insertCharacterWeapon(characterId: Long, name: String, ATKBonus: Int, damage: String, damageType: String, description: String, count: Int): Long
    @Query("DELETE FROM weapons WHERE characterId LIKE :characterId AND weaponId LIKE :weaponId")
    suspend fun deleteCharacterWeapon(characterId: Long, weaponId: Long)
    @Query("UPDATE weapons SET name = :name, ATKBonus = :ATKBonus, damage = :damage, damageType = :damageType, description = :description, count = :count WHERE characterId LIKE :characterId AND weaponId LIKE :weaponId")
    suspend fun updateCharacterWeapon(characterId: Long, weaponId: Long, name: String, ATKBonus: Int, damage: String, damageType: String, description: String, count: Int)

    @Query("INSERT INTO items (characterId, name, description, count) VALUES (:characterId, :name, :description, :count)")
    suspend fun insertCharacterItem(characterId: Long, name: String, description: String, count: Int): Long
    @Query("DELETE FROM items WHERE characterId LIKE :characterId AND itemId LIKE :itemId")
    suspend fun deleteCharacterItem(characterId: Long, itemId: Long)
    @Query("UPDATE items SET name = :name, description = :description, count = :count WHERE characterId LIKE :characterId AND itemId LIKE :itemId")
    suspend fun updateCharacterItem(characterId: Long, itemId: Long, name: String, description: String, count: Int)

    @Query("INSERT INTO features (characterId, name, description) VALUES (:characterId, :name, :description)")
    suspend fun insertCharacterFeature(characterId: Long, name: String, description: String): Long
    @Query("DELETE FROM features WHERE characterId LIKE :characterId AND featureId LIKE :featureId")
    suspend fun deleteCharacterFeature(characterId: Long, featureId: Long)
    @Query("UPDATE features SET name = :name, description = :description WHERE characterId LIKE :characterId AND featureId LIKE :featureId")
    suspend fun updateCharacterFeature(characterId: Long, featureId: Long, name: String, description: String)

    @Query("INSERT INTO proficiencies (characterId, name, description) VALUES (:characterId, :name, :description)")
    suspend fun insertCharacterProficiency(characterId: Long, name: String, description: String): Long
    @Query("DELETE FROM proficiencies WHERE characterId LIKE :characterId AND proficiencyId LIKE :featureId")
    suspend fun deleteCharacterProficiency(characterId: Long, featureId: Long)
    @Query("UPDATE proficiencies SET name = :name, description = :description WHERE characterId LIKE :characterId AND proficiencyId LIKE :featureId")
    suspend fun updateCharacterProficiency(characterId: Long, featureId: Long, name: String, description: String)

    @Query("INSERT INTO notes (characterId, name, text, priority, date, completed) VALUES (:characterId, :name, :text, :priority, :dateTime, :completed)")
    suspend fun insertCharacterNote(characterId: Long, name: String, text: String, priority: Int, dateTime: LocalDateTime, completed: Boolean): Long
    @Query("DELETE FROM notes WHERE characterId LIKE :characterId AND noteId LIKE :noteId")
    suspend fun deleteCharacterNote(characterId: Long, noteId: Long)
    @Query("UPDATE notes SET name = :name, text = :text, priority = :priority, date = :dateTime, completed = :completed WHERE characterId LIKE :characterId AND noteId LIKE :noteId")
    suspend fun updateCharacterNote(characterId: Long, noteId: Long, name: String, text: String, priority: Int, dateTime: LocalDateTime, completed: Boolean)

    @Query("UPDATE characters SET languages = :languages WHERE id LIKE :characterId")
    suspend fun updateLanguages(characterId: Long, languages: String)

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharactersDataBase>


}