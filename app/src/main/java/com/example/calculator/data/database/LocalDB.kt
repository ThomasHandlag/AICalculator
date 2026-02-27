package com.example.calculator.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.Date

@Database(
    entities = [CalHistory::class, ChatHistory::class, ChatMessage::class],
    version = 3,
    exportSchema = false,
)
abstract class LocalDB : RoomDatabase() {

    abstract fun calHistoryDAO(): CalHistoryDAO

    abstract fun chatHistoryDAO(): ChatHistoryDAO

    abstract fun chatMessageDAO(): ChatMessageDAO

    companion object {
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

@Entity
data class ChatHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val date: String = Date().toString(),
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ChatHistory::class,
            parentColumns = ["id"],
            childColumns = ["chatHistoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val chatHistoryId: Int,
    val message: String,
    val isUser: Boolean,
    val date: String = Date().toString(),
)