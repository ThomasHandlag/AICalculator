package com.example.calculator.ui.unit

import androidx.lifecycle.ViewModel
import com.example.calculator.ui.widgets.AreaUnit
import com.example.calculator.ui.widgets.CalUnit
import com.example.calculator.ui.widgets.CalUnitType
import com.example.calculator.ui.widgets.DataUnit
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
        if (_inputValue.value[0] == '0' && _inputValue.value.length > 1 && _inputValue.value[1] != '.') {
            _inputValue.value = _inputValue.value.substring(1)
        }
    }

    private val _resultValue = MutableStateFlow("0.00")
    val resultValue: MutableStateFlow<String> get() = _resultValue

    fun calResultValue() {
        when (convertType.value) {
            CalUnitType.LENGTH -> {
                val from = fromUnit.value
                val to = toUnit.value
                _resultValue.value =
                    convertLength(inputValue.value.toDoubleOrNull() ?: 0.0, from, to).toString()
            }

            CalUnitType.WEIGHT -> {
                val from = fromUnit.value as WeightUnit
                val to = toUnit.value as WeightUnit
                _resultValue.value =
                    convertWeight(inputValue.value.toDoubleOrNull() ?: 0.0, from, to).toString()
            }

            CalUnitType.VOLUME -> {
                val from = fromUnit.value as VolumeUnit
                val to = toUnit.value as VolumeUnit
                _resultValue.value =
                    convertVolume(inputValue.value.toDoubleOrNull() ?: 0.0, from, to).toString()
            }

            CalUnitType.AREA -> {
                val from = fromUnit.value as AreaUnit
                val to = toUnit.value as AreaUnit
                _resultValue.value =
                    convertArea(inputValue.value.toDoubleOrNull() ?: 0.0, from, to).toString()
            }

            CalUnitType.TIME -> {
                val from = fromUnit.value as TimeUnit
                val to = toUnit.value as TimeUnit
                _resultValue.value =
                    convertTime(inputValue.value.toDoubleOrNull() ?: 0.0, from, to).toString()
            }

            CalUnitType.DATA -> {
                val from = fromUnit.value as DataUnit
                val to = toUnit.value as DataUnit
                _resultValue.value =
                    convertData(inputValue.value.toDoubleOrNull() ?: 0.0, from, to).toString()
            }

            else -> {}
        }
    }

    fun convertLength(value: Double, from: CalUnit, to: CalUnit): Double {
        val valueInMeter = when (from) {
            LengthUnit.CM -> value / 100
            LengthUnit.KM -> value * 1000
            LengthUnit.M -> value
            LengthUnit.IN -> value * 0.0254
            LengthUnit.FT -> value * 0.3048
            LengthUnit.YD -> value * 0.9144
            LengthUnit.MI -> value * 1609.34
            else -> value
        }

        return when (to) {
            LengthUnit.CM -> valueInMeter * 100
            LengthUnit.KM -> valueInMeter / 1000
            LengthUnit.M -> valueInMeter
            LengthUnit.IN -> valueInMeter / 0.0254
            LengthUnit.FT -> valueInMeter / 0.3048
            LengthUnit.YD -> valueInMeter / 0.9144
            LengthUnit.MI -> valueInMeter / 1609.34
            else -> valueInMeter
        }
    }

    fun convertWeight(value: Double, from: CalUnit, to: CalUnit): Double {
        val valueInGram = when (from) {
            WeightUnit.G -> value
            WeightUnit.KG -> value * 1000
            WeightUnit.LB -> value * 453.592
            WeightUnit.OZ -> value * 28.3495
            else -> value
        }

        return when (to) {
            WeightUnit.G -> valueInGram
            WeightUnit.KG -> valueInGram / 1000
            WeightUnit.LB -> valueInGram / 453.592
            WeightUnit.OZ -> valueInGram / 28.3495
            else -> valueInGram
        }
    }

    fun convertVolume(value: Double, from: CalUnit, to: CalUnit): Double {
        val valueInLiter = when (from) {
            VolumeUnit.ML -> value / 1000
            VolumeUnit.L -> value
            VolumeUnit.GAL -> value * 3.78541
            VolumeUnit.PT -> value * 0.473176
            else -> value
        }

        return when (to) {
            VolumeUnit.ML -> valueInLiter * 1000
            VolumeUnit.L -> valueInLiter
            VolumeUnit.GAL -> valueInLiter / 3.78541
            VolumeUnit.PT -> valueInLiter / 0.473176
            else -> valueInLiter
        }
    }

    fun convertArea(value: Double, from: CalUnit, to: CalUnit): Double {
        val valueInSqMeter = when (from) {
            AreaUnit.SQM -> value
            AreaUnit.SQKM -> value * 1_000_000
            AreaUnit.SQFT -> value * 0.092903
            AreaUnit.SQCM -> value / 10_000
            AreaUnit.SQIN -> value * 0.00064516
            AreaUnit.SQMM -> value / 1_000_000
            AreaUnit.SQYARD -> value * 0.836127
            AreaUnit.SQMI -> value * 2_589_988.11
            else -> value
        }

        return when (to) {
            AreaUnit.SQM -> valueInSqMeter
            AreaUnit.SQKM -> valueInSqMeter / 1_000_000
            AreaUnit.SQFT -> valueInSqMeter / 0.092903
            AreaUnit.SQCM -> valueInSqMeter * 10_000
            AreaUnit.SQIN -> valueInSqMeter / 0.00064516
            AreaUnit.SQMM -> valueInSqMeter * 1_000_000
            AreaUnit.SQYARD -> valueInSqMeter / 0.836127
            AreaUnit.SQMI -> valueInSqMeter / 2_589_988.11
            else -> valueInSqMeter
        }
    }

    fun convertTime(value: Double, from: CalUnit, to: CalUnit): Double {
        val valueInSeconds = when (from) {
            TimeUnit.SEC -> value
            TimeUnit.MIN -> value * 60
            TimeUnit.HR -> value * 3600
            TimeUnit.DAY -> value * 86400
            else -> value
        }

        return when (to) {
            TimeUnit.SEC -> valueInSeconds
            TimeUnit.MIN -> valueInSeconds / 60
            TimeUnit.HR -> valueInSeconds / 3600
            TimeUnit.DAY -> valueInSeconds / 86400
            else -> valueInSeconds
        }
    }

    fun convertData(value: Double, from: CalUnit, to: CalUnit): Double {
        val valueInBytes = when (from) {
            DataUnit.B -> value
            DataUnit.KB -> value * 1024
            DataUnit.MB -> value * 1024 * 1024
            DataUnit.GB -> value * 1024 * 1024 * 1024
            DataUnit.TB -> value * 1024 * 1024 * 1024 * 1024
            else -> value
        }

        return when (to) {
            DataUnit.B -> valueInBytes
            DataUnit.KB -> valueInBytes / 1024
            DataUnit.MB -> valueInBytes / (1024 * 1024)
            DataUnit.GB -> valueInBytes / (1024 * 1024 * 1024)
            DataUnit.TB -> valueInBytes / (1024 * 1024 * 1024 * 1024)
            else -> valueInBytes
        }
    }
}