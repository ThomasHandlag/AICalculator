package com.example.calculator.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.calculator.CalculatorApplication
import com.example.calculator.ui.ai.GenAIViewModel
import com.example.calculator.ui.currency.CurrencyViewModel
import com.example.calculator.ui.home.AppViewModel
import com.example.calculator.ui.basic.BasicViewModel
import com.example.calculator.ui.history.HistoryViewModel
import com.example.calculator.ui.unit.UnitViewModel
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AppViewModel(
                this.calculatorApplication(),
                this.calculatorApplication().appContainer.userPreferenceRepository
            )
        }
        initializer {
            BasicViewModel(this.calculatorApplication().appContainer.calHistoryRepository)
        }
        initializer {
            HistoryViewModel(
                repo = this.calculatorApplication().appContainer.calHistoryRepository
            )
        }
        initializer {
            UnitViewModel()
        }
        initializer {
            CurrencyViewModel()
        }
        initializer {
            val model = Firebase.ai(backend = GenerativeBackend.googleAI())
                .generativeModel("gemini-3-flash-preview")
            GenAIViewModel(
                generativeModel = model,
                chatRepository = this.calculatorApplication().appContainer.chatHistoryRepository
            )
        }
    }
}

/**
 * Extension function to queries for [android.app.Application] object and returns an instance of
 * [CalculatorApplication].
 */
fun CreationExtras.calculatorApplication(): CalculatorApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CalculatorApplication)