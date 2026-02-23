package com.example.calculator.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.Date

@Database(
    entities = [CalHistory::class],
    version = 1,
    exportSchema = false,
)
abstract class LocalDB : RoomDatabase() {

    abstract fun calHistoryDAO(): CalHistoryDAO

    companion   object {
        @Volatile
        private var INSTANCE: LocalDB? = null

        fun getInstance(context: android.content.Context): LocalDB {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    LocalDB::class.java,
                    "calculator_database"
                ).fallbackToDestructiveMigration(true).build().also { INSTANCE = it }
            }
        }
    }
}


@Entity
data class CalHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val expression: String,
    val result: String,
    val date: String = Date().toString(),
)