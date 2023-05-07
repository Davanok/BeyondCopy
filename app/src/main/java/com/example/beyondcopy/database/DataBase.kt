package com.example.beyondcopy.database

import androidx.room.*

@Entity("characters")
data class CharactersDataBase(
    @PrimaryKey(true) val id: Long,

    @ColumnInfo("cName") var cName: String?,

    @ColumnInfo("cClass") var cClass: String?,
    @ColumnInfo("cRace") var cRace: String?,
    @ColumnInfo("cBackground") var cBackground: String?,

    @ColumnInfo("exp") var exp: Int?,
    @ColumnInfo("lvl") var lvl: Int?,

    @ColumnInfo("strength") var strength: Int?,
    @ColumnInfo("dexterity") var dexterity: Int?,
    @ColumnInfo("constitution") var constitution: Int?,
    @ColumnInfo("intelligence") var intelligence: Int?,
    @ColumnInfo("wisdom") var wisdom: Int?,
    @ColumnInfo("charisma") var charisma: Int?,

    @ColumnInfo("proficiencyBonus") var proficiencyBonus: Int?,

    @ColumnInfo("perception") var perception: Int?,

    @ColumnInfo("skills") var skills: String?,
    @ColumnInfo("savingThrows") var savingThrows: String?,

    @ColumnInfo("copper") var copper: Int?,

    @ColumnInfo("armorClass") var armorClass: Int?,
    @ColumnInfo("initiative") var initiative: Int?,
    @ColumnInfo("speed") var speed: Int?,

    @ColumnInfo("currentHitPoints") var currentHitPoints: Int?,
    @ColumnInfo("temporaryHitPoints") var temporaryHitPoints: Int?,
    @ColumnInfo("hitDice") var hitDice: Int?,

    @ColumnInfo("traits") var traits: String?,
    @ColumnInfo("ideals") var ideals:  String?,
    @ColumnInfo("bonds") var bonds: String?,
    @ColumnInfo("flaws") var flaws: String?,

    @ColumnInfo("languages") var languages: String?,
    @ColumnInfo("proficiencies") var proficiencies: String?,
    @ColumnInfo("equipment") var equipment: String?,
    @ColumnInfo("features") var features: String?,

)
@Entity("weapons")
data class WeaponsDataBase(
    @PrimaryKey(true) val weaponId: Long,
    @ColumnInfo("characterId") val CharacterId: Long,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("ATKBonus") var ATKBonus: Int,
    @ColumnInfo("damage") var damage: String,
    @ColumnInfo("damageType") var damageType: String,
    @ColumnInfo("description") var description: String
)
@Entity("items")
data class ItemsDataBase(
    @PrimaryKey(true) val itemId: Long,
    @ColumnInfo("characterId") val CharacterId: Long,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("description") var description: String
)
data class CharacterWithAllDataBase(
    @Embedded val character: CharactersDataBase,

    @Relation(WeaponsDataBase::class, "id", "characterId")
    var weapons: List<WeaponsDataBase>,
    @Relation(ItemsDataBase::class, "id", "characterId")
    var items: List<ItemsDataBase>

)

@Dao
interface DataBaseDao{
    @Query("DELETE FROM characters")
    suspend fun deleteAll()

    @Query("SELECT * FROM characters WHERE id LIKE :id")
    suspend fun getCharacter(id: Long): CharactersDataBase

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharactersDataBase>

    @Query("INSERT INTO characters (cName, cClass, cRace, cBackground, exp, lvl, proficiencyBonus, speed, initiative, armorClass) VALUES (:cName, :cClass, :cRace, :cBackground, :exp, :lvl, :proficiencyBonus, :speed, :initiative, :armorClass)")
    suspend fun insertCharactersMain(cName: String, cClass: String, cRace: String, cBackground: String, exp: Int, lvl: Int, proficiencyBonus: Int, speed: Int, initiative: Int, armorClass: Int): Long

    suspend fun updateStatsSimple(id: Long, stats: IntArray, proficiencyBonus: Int, perception: Int, skills: IntArray, savingThrows: IntArray){
        val rSkills = skills.joinToString("|")
        val rSaves = savingThrows.joinToString("|")
        updateStats(id, stats[0], stats[1], stats[2], stats[3], stats[4], stats[5], proficiencyBonus, perception, rSkills, rSaves)
    }

    @Query("UPDATE characters SET strength = :strength, dexterity = :dexterity, constitution = :constitution, intelligence = :intelligence, wisdom = :wisdom, charisma = :charisma, proficiencyBonus = :proficiencyBonus, perception = :perception, skills = :skills, savingThrows = :savingThrows WHERE id LIKE :id")
    suspend fun updateStats(id: Long, strength: Int, dexterity: Int, constitution: Int, intelligence: Int, wisdom: Int, charisma: Int, proficiencyBonus: Int, perception: Int, skills: String, savingThrows: String)

    @Query("UPDATE characters SET traits = :traits, ideals = :ideals, bonds = :bonds, flaws = :flaws WHERE id LIKE :id")
    suspend fun updatePersonality(id: Long, traits: String, ideals: String, bonds: String, flaws: String)

    @Transaction
    @Query("SELECT * FROM characters WHERE id LIKE :id LIMIT 1")
    suspend fun getCharacterWithAll(id: Long): CharacterWithAllDataBase

    @Query("INSERT INTO weapons (characterId, name, ATKBonus, damage, damageType, description) VALUES (:characterId, :name, :ATKBonus, :damage, :damageType, :description)")
    suspend fun insertCharacterWeapon(characterId: Long, name: String, ATKBonus: Int, damage: String, damageType: String, description: String)

    @Query("INSERT INTO items (characterId, name, description) VALUES (:characterId, :name, :description)")
    suspend fun insertCharacterItem(characterId: Long, name: String, description: String)

}

@Database(entities = [CharactersDataBase::class, WeaponsDataBase::class, ItemsDataBase::class],
    version = 5, exportSchema = false)
abstract class DataBase: RoomDatabase(){
    abstract fun getDao(): DataBaseDao
}
