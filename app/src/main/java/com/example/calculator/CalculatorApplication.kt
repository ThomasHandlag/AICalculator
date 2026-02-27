package com.example.calculator

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.calculator.data.repository.AppContainer
import com.example.calculator.data.repository.DefaultAppContainer
import com.google.firebase.FirebaseApp

private const val STORE_PREFERENCE_NAME = "store_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = STORE_PREFERENCE_NAME
)

class CalculatorApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        appContainer = DefaultAppContainer(this, dataStore)
    }
}