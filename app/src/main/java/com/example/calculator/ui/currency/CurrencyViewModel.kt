package com.example.calculator.ui.currency

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CurrencyViewModel : ViewModel() {

    private val _fromCurrency = MutableStateFlow(Currency.USD)
    val fromCurrency: StateFlow<Currency> = _fromCurrency

    private val _toCurrency = MutableStateFlow(Currency.EUR)
    val toCurrency: StateFlow<Currency> get() = _toCurrency

    fun setFromCurrency(currency: Currency) {
        _fromCurrency.value = currency
    }

    fun setToCurrency(currency: Currency) {
        _toCurrency.value = currency
    }

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> get() = _amount
    fun setAmount(value: String) {
        _amount.value = value
    }

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> get() = _result
    fun setResult(value: String) {
        _result.value = value
    }

    fun clear() {
        _amount.value = ""
        _result.value = ""
    }

    @SuppressLint("DefaultLocale")
    fun convert() {
        val from = _fromCurrency.value
        val to = _toCurrency.value
        val amount = _amount.value.toDoubleOrNull() ?: return

        val rate = getConversionRate(from, to)
        val convertedAmount = amount * rate
        _result.value = String.format("%.2f", convertedAmount)
    }

    private fun getConversionRate(from: Currency, to: Currency): Double {
        return when (from) {
            Currency.USD -> when (to) {
                Currency.EUR -> 0.85
                Currency.JPY -> 110.0
                else -> 1.0
            }

            Currency.EUR -> when (to) {
                Currency.USD -> 1.18
                Currency.JPY -> 129.0
                else -> 1.0
            }

            Currency.JPY -> when (to) {
                Currency.USD -> 0.0091
                Currency.EUR -> 0.0077
                else -> 1.0
            }

            else -> 1.0
        }
    }
}