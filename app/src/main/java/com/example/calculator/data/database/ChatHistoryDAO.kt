package com.example.calculator.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChatHistoryDAO {

    @Query("SELECT * FROM ChatHistory ORDER BY date DESC LIMIT :limit OFFSET :offset")

    suspend fun getAllChatHistories(
        limit: Int,
        offset: Int
    ): List<ChatHistory>

    @Query("SELECT * FROM ChatHistory WHERE id = :id")
    suspend fun getChatHistoryById(id: Int): ChatHistory?

    @Delete
    suspend fun delete(chatHistory: ChatHistory)

    @Insert
    suspend fun insert(chatHistory: ChatHistory)

    @Query("SELECT * FROM ChatHistory ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedChatHistory(): ChatHistory?
}