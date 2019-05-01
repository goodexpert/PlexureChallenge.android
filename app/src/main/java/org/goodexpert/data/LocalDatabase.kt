package org.goodexpert.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.goodexpert.data.converter.FeatureListConverter
import org.goodexpert.data.dao.StoreDao
import org.goodexpert.data.entity.Store

@Database(entities = [Store::class], version = LocalDatabase.VERSION, exportSchema = false)
@TypeConverters(FeatureListConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun storeDao(): StoreDao

    companion object {
        const val DATABASE_NAME = "local_database"
        const val VERSION = 1

        // For Singleton instantiation
        @Volatile private var instance: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return instance ?: synchronized(LocalDatabase::class) {
                instance ?: Room.databaseBuilder(context.applicationContext,
                    LocalDatabase::class.java, DATABASE_NAME).build().also { instance = it }
            }
        }
    }
}