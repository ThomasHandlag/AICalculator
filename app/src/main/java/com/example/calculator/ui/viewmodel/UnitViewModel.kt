package com.example.calculator.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calculator.ui.widgets.AreaUnit
import com.example.calculator.ui.widgets.CalUnit
import com.example.calculator.ui.widgets.CalUnitType
import com.example.calculator.ui.widgets.LengthUnit
import com.example.calculator.ui.widgets.SpeedUnit
import com.example.calculator.ui.widgets.TimeUnit
import com.example.calculator.ui.widgets.VolumeUnit
import com.example.calculator.ui.widgets.WeightUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UnitViewModel : ViewModel() {

    private val _convertType = MutableStateFlow(
        value = CalUnitType.LENGTH
    )

    val convertType: MutableStateFlow<CalUnitType> get() = _convertType

    fun setConvertType(type: CalUnitType) {
        _convertType.value = type
        when (type) {
            CalUnitType.LENGTH -> {
                _fromUnit.value = LengthUnit.CM
                _toUnit.value = LengthUnit.M
            }

            CalUnitType.WEIGHT -> {
                _fromUnit.value = WeightUnit.G
                _toUnit.value = WeightUnit.KG
            }

            CalUnitType.VOLUME -> {
                _fromUnit.value = VolumeUnit.ML
                _toUnit.value = VolumeUnit.L
            }

            CalUnitType.AREA -> {
                _fromUnit.value = AreaUnit.SQM
                _toUnit.value = AreaUnit.SQKM
            }

            CalUnitType.TIME -> {
                _fromUnit.value = TimeUnit.HR
                _toUnit.value = TimeUnit.DAY
            }

            CalUnitType.SPEED -> {
                _fromUnit.value = SpeedUnit.KPH
                _toUnit.value = SpeedUnit.MPH
            }

            else -> {}
        }
    }

    private val _fromUnit = MutableStateFlow<CalUnit>(LengthUnit.CM)
    private val _toUnit = MutableStateFlow<CalUnit>(LengthUnit.M)

    val fromUnit: StateFlow<CalUnit> get() = _fromUnit
    val toUnit: StateFlow<CalUnit> get() = _toUnit

    fun setFromUnit(unit: CalUnit) {
        _fromUnit.value = unit
    }

    fun setToUnit(unit: CalUnit) {
        _toUnit.value = unit
    }

    private val _inputValue = MutableStateFlow("0")
    val inputValue: MutableStateFlow<String> get() = _inputValue

    fun setInputValue(value: String) {
        _inputValue.value = value
    }

    private val _resultValue = MutableStateFlow("0.00")
    val resultValue: MutableStateFlow<String> get() = _resultValue

    fun calResultValue() {

    }
}