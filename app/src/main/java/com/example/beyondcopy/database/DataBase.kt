package com.example.beyondcopy.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Embedded
import androidx.room.Relation
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

class Converters{
    @TypeConverter
    fun toBooleanList(str: String?): List<Boolean>? {
        return str?.split("|")?.map { it == "true" }?.toList()
    }
    @TypeConverter
    fun fromBooleanList(list: List<Boolean>): String{
        return list.joinToString("|")
    }
    @TypeConverter
    fun toIntList(str: String?): List<Int>?{
        return str?.split("|")?.map{it.toInt()}?.toList()
    }
    @TypeConverter
    fun fromIntList(list: List<Int>): String{
        return list.joinToString("|")
    }

    @TypeConverter
    fun toLocalDateTime(string: String): LocalDateTime{
        return LocalDateTime.parse(string, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): String{
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}
data class CharacterWithAllDataBase(
    @Embedded val character: CharactersDataBase,

    @Relation(WeaponsDataBase::class, "id", "characterId")
    val weapons: List<WeaponsDataBase>,
    @Relation(ItemsDataBase::class, "id", "characterId")
    val items: List<ItemsDataBase>,
    @Relation(FeaturesDataBase::class, "id", "characterId")
    val features: List<FeaturesDataBase>,
    @Relation(ProficienciesDataBase::class, "id", "characterId")
    val proficiencies: List<ProficienciesDataBase>,
    @Relation(NotesDataBase::class, "id", "characterId")
    val notes: List<NotesDataBase>,
)
@Database(entities = [
    CharactersDataBase::class,
    WeaponsDataBase::class,
    ItemsDataBase::class,
    FeaturesDataBase::class,
    ProficienciesDataBase::class,
    NotesDataBase::class,
                     ],
    version = 10, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DataBase: RoomDatabase(){
    abstract fun getDao(): DataBaseDao
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
