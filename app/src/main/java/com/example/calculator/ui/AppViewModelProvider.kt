package com.example.calculator.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.calculator.CalculatorApplication
import com.example.calculator.ui.currency.CurrencyViewModel
import com.example.calculator.ui.viewmodel.AppViewModel
import com.example.calculator.ui.viewmodel.BasicViewModel
import com.example.calculator.ui.viewmodel.HistoryViewModel
import com.example.calculator.ui.viewmodel.UnitViewModel

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
    }
}

/**
 * Extension function to queries for [android.app.Application] object and returns an instance of
 * [CalculatorApplication].
 */
fun CreationExtras.calculatorApplication(): CalculatorApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as CalculatorApplication)