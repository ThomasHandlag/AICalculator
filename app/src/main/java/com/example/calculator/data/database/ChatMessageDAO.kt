package com.example.calculator.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChatMessageDAO {

    @Query("SELECT * FROM ChatMessage WHERE chatHistoryId = :chatHistoryId ORDER BY date ASC")
    suspend fun getMessagesByChatHistoryId(chatHistoryId: Int): List<ChatMessage>

    @Insert
    suspend fun insert(chatMessage: ChatMessage)

    @Query("DELETE FROM ChatMessage WHERE chatHistoryId = :chatHistoryId")
    suspend fun deleteChatMessagesByChatHistoryId(chatHistoryId: Int)
}