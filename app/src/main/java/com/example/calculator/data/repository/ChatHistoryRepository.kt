package com.example.calculator.data.repository

import com.example.calculator.data.database.ChatHistory
import com.example.calculator.data.database.ChatHistoryDAO
import com.example.calculator.data.database.ChatMessage
import com.example.calculator.data.database.ChatMessageDAO


interface ChatHistoryRepository {

    suspend fun getChatHistories(
        limit: Int,
        offset: Int
    ): List<ChatHistory>

    suspend fun getChatHistoryById(id: Int): ChatHistory?

    suspend fun deleteChatHistory(chatHistory: ChatHistory)

    suspend fun insertChatHistory(chatHistory: ChatHistory)

    suspend fun getChatMessagesByChatHistoryId(chatHistoryId: Int): List<ChatMessage>

    suspend fun insertChatMessage(chatMessage: ChatMessage)

    suspend fun getLastInsertedChatHistory(): ChatHistory?
}

class ChatHistoryRepositoryImpl(
    val chatHistoryDAO: ChatHistoryDAO,
    val chatMessageDAO: ChatMessageDAO
) : ChatHistoryRepository {
    override suspend fun getChatHistories(limit: Int, offset: Int): List<ChatHistory> {
        return chatHistoryDAO.getAllChatHistories(limit, offset)
    }

    override suspend fun getChatHistoryById(id: Int): ChatHistory? {
        return chatHistoryDAO.getChatHistoryById(id)
    }

    override suspend fun deleteChatHistory(chatHistory: ChatHistory) {
        chatHistoryDAO.delete(chatHistory)
    }

    override suspend fun insertChatHistory(chatHistory: ChatHistory) {
        chatHistoryDAO.insert(chatHistory)
    }

    override suspend fun getChatMessagesByChatHistoryId(chatHistoryId: Int): List<ChatMessage> {
        return chatMessageDAO.getMessagesByChatHistoryId(chatHistoryId)
    }

    override suspend fun insertChatMessage(chatMessage: ChatMessage) {
        chatMessageDAO.insert(chatMessage)
    }

    override suspend fun getLastInsertedChatHistory(): ChatHistory? {
        return chatHistoryDAO.getLastInsertedChatHistory()
    }
}