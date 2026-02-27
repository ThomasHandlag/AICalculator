package com.example.calculator.data.repository


import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.calculator.enums.AppLanguage
import com.example.calculator.enums.AppThemeMode
import com.example.calculator.enums.valueOfCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class UserPreferenceRepository(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val LANGUAGE = stringPreferencesKey(name = "language")
        private val THEME_MODE = stringPreferencesKey(name = "theme_mode")
        private val FIRST_LAUNCH = booleanPreferencesKey(name = "first_launch")
    }

    val language: Flow<AppLanguage> = dataStore.data.catch { exception ->
        run {
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }
    }.map { preferences ->
        when (preferences[LANGUAGE]) {
            null -> AppLanguage.ENGLISH
            else -> valueOfCode(preferences[LANGUAGE]!!)
        }
    }

    val themeMode: Flow<AppThemeMode> = dataStore.data.catch { exception ->
        run {
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }
    }.map { preferences ->
        when (preferences[LANGUAGE]) {
            null -> AppThemeMode.SYSTEM
            else -> AppThemeMode.entries.firstOrNull { it.code == preferences[THEME_MODE] }
                ?: AppThemeMode.SYSTEM
        }
    }

    val firstLaunch: Boolean
        get() = runBlocking {
            val prefs = dataStore.data.first()
            prefs[FIRST_LAUNCH] ?: true
        }

    suspend fun setFirstLaunch() {
        dataStore.edit { preferences ->
            preferences[FIRST_LAUNCH] = false
        }
    }

    suspend fun updateThemeMode(themeMode: AppThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = themeMode.code
        }
    }

    suspend fun updateLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }
}