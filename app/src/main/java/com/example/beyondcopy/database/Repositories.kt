package com.example.beyondcopy.database

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class DataRepository @Inject constructor(
    private val Dao: DataBaseDao
){
    suspend fun getCharacter(id: Long) = Dao.getCharacter(id)
    suspend fun getAllCharacters() = Dao.getAllCharacters()
    suspend fun insertCharactersMain(cName: String, cClass: String, cRace: String, cBackground: String, exp: Int, lvl: Int, proficiencyBonus: Int, speed: Int, initiative: Int, armorClass: Int): Long =
        Dao.insertCharactersMain(cName, cClass, cRace, cBackground, exp, lvl, proficiencyBonus, speed, initiative, armorClass)
    suspend fun updateStatsSimple(id: Long, stats: IntArray, proficiencyBonus: Int, perception: Int, skills: IntArray, savingThrows: IntArray) =
        Dao.updateStatsSimple(id, stats, proficiencyBonus, perception, skills, savingThrows)
    suspend fun updatePersonality(id: Long, traits: String, ideals: String, bonds: String, flaws: String) =
        Dao.updatePersonality(id, traits, ideals, bonds, flaws)

    suspend fun getCharacterWithAll(id: Long): CharacterWithAllDataBase = Dao.getCharacterWithAll(id)
    suspend fun insertCharacterWeapon(characterId: Long, name: String, ATKBonus: Int, damage: String, damageType: String, description: String) =
        Dao.insertCharacterWeapon(characterId, name, ATKBonus, damage, damageType, description)
    suspend fun insertCharacterItem(characterId: Long, name: String, description: String) =
        Dao.insertCharacterItem(characterId, name, description)

    suspend fun deleteAll() = Dao.deleteAll()
}
@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    @Singleton
    @Provides
    fun provideDataBase( @ApplicationContext app: Context) =
        Room.databaseBuilder(
        app, DataBase::class.java, "subjectDataBase"
    ).fallbackToDestructiveMigration().build()
    @Singleton
    @Provides
    fun provideDao(db: DataBase) = db.getDao()
}
@HiltAndroidApp
class BaseApplication : Application()