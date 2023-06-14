package com.example.beyondcopy.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity("characters")
data class CharactersDataBase(
    @PrimaryKey(true) val id: Long,

    @ColumnInfo("cName") var cName: String?,

    @ColumnInfo("cClass") var cClass: String?,
    @ColumnInfo("cRace") var cRace: String?,
    @ColumnInfo("cBackground") var cBackground: String?,

    @ColumnInfo("exp") var exp: Int?,
    @ColumnInfo("lvl") var lvl: Int?,

    @ColumnInfo("stats") var stats: List<Int>?,

    @ColumnInfo("proficiencyBonus") var proficiencyBonus: Int?,

    @ColumnInfo("perception") var perception: Int?,

    @ColumnInfo("skills") var skills: List<Boolean>?,
    @ColumnInfo("savingThrows") var savingThrows: List<Boolean>?,

    @ColumnInfo("copper") var copper: Int?,

    @ColumnInfo("armorClass") var armorClass: Int?,
    @ColumnInfo("initiative") var initiative: Int?,
    @ColumnInfo("speed") var speed: Int?,

    @ColumnInfo("maxHitPoints") var maxHitPoints: Int?,
    @ColumnInfo("currentHitPoints") var currentHitPoints: Int?,
    @ColumnInfo("temporaryHitPoints") var temporaryHitPoints: Int?,
    @ColumnInfo("hitDice") var hitDice: Int?,

    @ColumnInfo("traits") var traits: String?,
    @ColumnInfo("ideals") var ideals:  String?,
    @ColumnInfo("bonds") var bonds: String?,
    @ColumnInfo("flaws") var flaws: String?,

    @ColumnInfo("languages") var languages: String?
    )
@Entity("weapons")
data class WeaponsDataBase(
    @PrimaryKey(true) val weaponId: Long,
    @ColumnInfo("characterId") val CharacterId: Long,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("ATKBonus") var ATKBonus: Int,
    @ColumnInfo("damage") var damage: String,
    @ColumnInfo("damageType") var damageType: String,
    @ColumnInfo("description") var description: String,
    @ColumnInfo("count") var count: Int
)
@Entity("items")
data class ItemsDataBase(
    @PrimaryKey(true) val itemId: Long,
    @ColumnInfo("characterId") val CharacterId: Long,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("description") var description: String,
    @ColumnInfo("count") var count: Int
)
@Entity("features")
data class FeaturesDataBase(
    @PrimaryKey(true) val featureId: Long,
    @ColumnInfo("characterId") val characterId: Long,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("description") var description: String
)
@Entity("proficiencies")
data class ProficienciesDataBase(
    @PrimaryKey(true) val proficiencyId: Long,
    @ColumnInfo("characterId") val characterId: Long,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("description") var description: String
)
@Entity("notes")
data class NotesDataBase(
    @PrimaryKey(true) val noteId: Long,
    @ColumnInfo("characterId") val characterId: Long,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("text") var text: String,
    @ColumnInfo("priority") var priority: Int,
    @ColumnInfo("date") var date: LocalDateTime,
    @ColumnInfo("completed") var completed: Boolean,
)