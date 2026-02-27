package com.example.calculator.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.calculator.data.database.LocalDB

interface AppContainer {
    val userPreferenceRepository: UserPreferenceRepository
    val calHistoryRepository: CalHistoryRepository
    val chatHistoryRepository: ChatHistoryRepository
}

class DefaultAppContainer(
    context: android.content.Context,
    dataStore: DataStore<Preferences>
) : AppContainer {
    override val userPreferenceRepository: UserPreferenceRepository by lazy {
        UserPreferenceRepository(dataStore)
    }

    override val calHistoryRepository: CalHistoryRepository by lazy {
        CalHistoryRepositoryImpl(
            calHistoryDAO = LocalDB.getInstance(context).calHistoryDAO()
        )
    }

    override val chatHistoryRepository: ChatHistoryRepository by lazy {
        ChatHistoryRepositoryImpl(
            chatHistoryDAO = LocalDB.getInstance(context).chatHistoryDAO(),
            chatMessageDAO = LocalDB.getInstance(context).chatMessageDAO()
        )
    }
}

