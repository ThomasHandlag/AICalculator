package com.example.calculator.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.data.repository.UserPreferenceRepository
import com.example.calculator.enums.AppLanguage
import com.example.calculator.enums.AppThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(application: Application, val preference: UserPreferenceRepository) :
    AndroidViewModel(application) {

    val language: StateFlow<AppLanguage>
        get() = preference.language.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(3000),
            initialValue = AppLanguage.ENGLISH
        )

    val theme: StateFlow<AppThemeMode>
        get() = preference.themeMode.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(3000),
            initialValue = AppThemeMode.SYSTEM
        )

    fun setTheme(value: AppThemeMode) {
        viewModelScope.launch {
            preference.updateThemeMode(value)
        }
    }

    fun setFirstLaunch() {
        viewModelScope.launch {
            preference.setFirstLaunch()
        }
    }

    val isFirstLaunch: Boolean
        get() = preference.firstLaunch

    fun setLanguage(value: String) {
        viewModelScope.launch {
            preference.updateLanguage(value)
        }
    }
}