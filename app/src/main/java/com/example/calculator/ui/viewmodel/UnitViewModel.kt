package com.example.calculator.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calculator.ui.widgets.CalUnitType
import kotlinx.coroutines.flow.MutableStateFlow

class UnitViewModel : ViewModel() {

    private val _convertType = MutableStateFlow(
        value = CalUnitType.LENGTH
    )

    val convertType: MutableStateFlow<CalUnitType> get() = _convertType

    fun setConvertType(type: CalUnitType) {
        _convertType.value = type
    }

    private val _fromUnit = MutableStateFlow("")
    private val _toUnit = MutableStateFlow("")

    val fromUnit: MutableStateFlow<String> get() = _fromUnit
    val toUnit: MutableStateFlow<String> get() = _toUnit

    fun setFromUnit(unit: String) {
        _fromUnit.value = unit
    }

    fun setToUnit(unit: String) {
        _toUnit.value = unit
    }

    private val _inputValue = MutableStateFlow("")
    val inputValue: MutableStateFlow<String> get() = _inputValue

    fun setInputValue(value: String) {
        _inputValue.value = value
    }

    private val _resultValue = MutableStateFlow("")
    val resultValue: MutableStateFlow<String> get() = _resultValue

    fun calResultValue() {

    }
}