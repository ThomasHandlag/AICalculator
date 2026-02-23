package com.example.calculator.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.data.repository.UserPreferenceRepository
import com.example.calculator.enums.AppLanguage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(application: Application, val preference: UserPreferenceRepository) :
    AndroidViewModel(application) {

    val language: StateFlow<AppLanguage> = preference.language.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = AppLanguage.ENGLISH
    )

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