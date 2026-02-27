package com.example.calculator.ui.ai

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.data.database.ChatHistory
import com.example.calculator.data.database.ChatMessage
import com.example.calculator.data.repository.ChatHistoryRepository
import com.google.firebase.ai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class GenAIViewModel(
    private val generativeModel: GenerativeModel,
    private val chatRepository: ChatHistoryRepository
) : ViewModel() {

    private val _currentChatId = MutableStateFlow<Int?>(null)
    val currentChatId: StateFlow<Int?> = _currentChatId

    fun setId(id: Int) {
        _currentChatId.value = id
    }

    private val _chatHistories = MutableStateFlow<List<ChatHistory>>(emptyList())
    val chatHistories: StateFlow<List<ChatHistory>> = _chatHistories

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages

    fun loadChatHistories() {
        viewModelScope.launch {
            val histories = chatRepository.getChatHistories(limit = 100, offset = 0)
            _chatHistories.value = histories
        }
    }

    fun loadChatMessages(chatHistoryId: Int) {
        viewModelScope.launch {
            val messages = chatRepository.getChatMessagesByChatHistoryId(chatHistoryId)
            _chatMessages.value = messages
            _currentChatId.value = chatHistoryId
        }
    }

    private suspend fun newChat(): Int? {
        chatRepository.insertChatHistory(
            chatHistory = ChatHistory(
                id = 0,
                title = "New Chat",
            )
        )
        val newChatHistory = chatRepository.getLastInsertedChatHistory()
        if (newChatHistory != null) {
            _currentChatId.value = newChatHistory.id
            _chatMessages.value = emptyList()
        }
        return newChatHistory?.id
    }

    fun insertChatMessage(chatHistoryId: Int?, message: String, isUser: Boolean) {
        viewModelScope.launch {
            if (chatHistoryId != null) {
                chatRepository.insertChatMessage(
                    ChatMessage(
                        id = 0,
                        chatHistoryId = chatHistoryId,
                        message = message,
                        isUser = isUser,
                        date = Calendar.getInstance().time.toString()
                    )
                )
                _chatMessages.value =
                    chatRepository.getChatMessagesByChatHistoryId(chatHistoryId)
            } else {
                val newChatHistoryId = newChat()
                if (newChatHistoryId != null) {
                    chatRepository.insertChatMessage(
                        ChatMessage(
                            id = 0,
                            chatHistoryId = newChatHistoryId,
                            message = message,
                            isUser = isUser,
                            date = Calendar.getInstance().time.toString()
                        )
                    )
                    _chatMessages.value =
                        chatRepository.getChatMessagesByChatHistoryId(newChatHistoryId)
                }
            }
        }
    }

    fun generateResponse(
        prompt: String,
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                insertChatMessage(
                    currentChatId.value,
                    prompt,
                    isUser = true
                )
                val response = generativeModel.generateContent(prompt)
                val id = currentChatId.value
                insertChatMessage(id, response.text ?: "Something goes wrong", isUser = false)
                onSuccess()
            } catch (e: Exception) {
                Log.e(
                    "GenAIViewModel",
                    "Error generating response: ${e.message} ${e.stackTrace}"
                )
                onError(e)
            }
        }
    }
}