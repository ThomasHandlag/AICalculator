package com.example.calculator.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun UnitSelectorPreview() {
    UnitSelector(
        unitType = CalUnitType.LENGTH,
        onSelect = {},
        fromUnit = LengthUnit.CM,
        toUnit = LengthUnit.M,
        selectedUnit = LengthUnit.KM,
        selectFor = true,
        sheetState = rememberModalBottomSheetState { true },
        show = true
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitSelector(
    unitType: CalUnitType = CalUnitType.LENGTH,
    onSelect: (CalUnit?) -> Unit = {},
    fromUnit: CalUnit? = null,
    toUnit: CalUnit? = null,
    selectedUnit: CalUnit? = null,
    selectFor: Boolean,
    sheetState: SheetState,
    show: Boolean = false
) {
    val unitList = when (unitType) {
        CalUnitType.LENGTH -> listOf(
            LengthUnit.CM,
            LengthUnit.KM,
            LengthUnit.M,
            LengthUnit.IN,
            LengthUnit.FT,
            LengthUnit.YD,
            LengthUnit.MI
        )

        CalUnitType.WEIGHT -> listOf(
            WeightUnit.G,
            WeightUnit.KG,
            WeightUnit.LB,
            WeightUnit.OZ
        )

        CalUnitType.VOLUME -> listOf(
            VolumeUnit.ML,
            VolumeUnit.L,
            VolumeUnit.GAL,
            VolumeUnit.PT
        )

//        CalUnitType.TEMPERATURE -> listOf(
//            TemperatureUnit.C,
//            TemperatureUnit.F,
//            TemperatureUnit.K
//        )

        CalUnitType.AREA -> listOf(
            AreaUnit.SQM,
            AreaUnit.SQKM,
            AreaUnit.SQFT,
            AreaUnit.SQCM,
            AreaUnit.SQIN,
            AreaUnit.SQMM,
            AreaUnit.SQYARD,
            AreaUnit.SQMI
        )

        CalUnitType.SPEED -> listOf(
            SpeedUnit.MPS,
            SpeedUnit.KPH,
            SpeedUnit.MPH
        )

        CalUnitType.TIME -> listOf(
            TimeUnit.SEC,
            TimeUnit.MIN,
            TimeUnit.HR,
            TimeUnit.DAY
        )

        CalUnitType.DATA -> listOf(
            DataUnit.B,
            DataUnit.KB,
            DataUnit.MB,
            DataUnit.GB,
            DataUnit.TB
        )

        else -> emptyList()
    }

    if (show) ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onSelect(null)
        },
        containerColor = Color.White,
        dragHandle = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Choose Unit", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                IconButton(
                    onClick = {
                        onSelect(null)
                    }) {
                    Icon(Icons.Rounded.Close, contentDescription = "")
                }
            }
        }
    ) {
        Surface(color = Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (unit in unitList) {
                        item {
                            SelectorItem(
                                title = {
                                    Text(
                                        text = unit.label,
                                        color = if (unit.unit == (if (selectFor) fromUnit else toUnit)?.unit) Color.Black else Color.White,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                                    )
                                },
                                label = {
                                    Text(
                                        text = unit.unit,
                                        color = Color(0xFF66707E),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                unit = unit,
                                selected = unit == selectedUnit,
                                onSelect = {
                                    if (unit.unit != (if (selectFor) fromUnit else toUnit)?.unit)
                                        onSelect(unit)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

abstract class CalUnit(val unit: String, val label: String)

sealed class LengthUnit(unit: String, label: String) : CalUnit(unit, label) {
    object CM : CalUnit("cm", "Centimeter")
    object KM : CalUnit("km", "Kilometer")
    object M : CalUnit("m", "Meter")
    object IN : CalUnit("in", "Inch")
    object FT : CalUnit("ft", "Foot")
    object YD : CalUnit("yd", "Yard")
    object MI : CalUnit("mi", "Mile")
}

sealed class WeightUnit(unit: String, label: String) : CalUnit(unit, label) {
    object G : CalUnit("g", "Gram")
    object KG : CalUnit("kg", "Kilogram")
    object LB : CalUnit("lb", "Pound")
    object OZ : CalUnit("oz", "Ounce")
}

sealed class VolumeUnit(unit: String, label: String) : CalUnit(unit, label) {
    object ML : CalUnit("mL", "Milliliter")
    object L : CalUnit("L", "Liter")
    object GAL : CalUnit("gal", "Gallon")
    object PT : CalUnit("pt", "Pint")
    object CL : CalUnit("cL", "Centiliter")
    object DL : CalUnit("dL", "Deciliter")
    object HL : CalUnit("hL", "Hectoliter")
    object KL : CalUnit("kL", "Kiloliter")
}

sealed class TemperatureUnit(unit: String, label: String) {
    object C : CalUnit("°C", "Celsius")
    object F : CalUnit("°F", "Fahrenheit")
    object K : CalUnit("K", "Kelvin")
}

sealed class AreaUnit(unit: String, label: String) : CalUnit(unit, label) {
    object SQM : CalUnit("m²", "Square Meter")
    object SQKM : CalUnit("km²", "Square Kilometer")
    object SQFT : CalUnit("ft²", "Square Foot")
    object SQCM : CalUnit(
        "cm²",
        "Square Centimeter"
    )

    object SQIN : CalUnit("in²", "Square Inch")
    object SQMM : CalUnit("mm²", "Square Millimeter")
    object SQYARD : CalUnit("yd²", "Square Yard")
    object SQMI : CalUnit("mi²", "Square Mile")
}

sealed class SpeedUnit(unit: String, label: String) : CalUnit(unit, label) {
    object MPS : CalUnit("m/s", "Meters per Second")
    object KPH : CalUnit("km/h", "Kilometers per Hour")
    object MPH : CalUnit(
        "mph",
        "Miles per Hour"
    )
}

sealed class TimeUnit(unit: String, label: String) : CalUnit(unit, label) {
    object SEC : CalUnit("s", "Second")
    object MIN : CalUnit("min", "Minute")
    object HR : CalUnit("hr", "Hour")
    object DAY : CalUnit("day", "Day")
    object MILLISEC : CalUnit("ms", "Millisecond")
    object NANOSEC : CalUnit("ns", "Nanosecond")
    object MICROSEC : CalUnit("μs", "Microsecond")
}

sealed class DataUnit(unit: String, label: String) : CalUnit(unit, label) {
    object B : CalUnit("B", "Byte")
    object KB : CalUnit("KB", "Kilobyte")
    object MB : CalUnit("MB", "Megabyte")
    object GB : CalUnit("GB", "Gigabyte")
    object TB : CalUnit(
        "TB",
        "Terabyte"
    )

    object BIT : CalUnit("bit", "Bit")
    object KBIT : CalUnit("Kbit", "Kilobit")
    object MBIT : CalUnit("Mbit", "Megabit")
    object GBIT : CalUnit("Gbit", "Gigabit")
}

enum class CalUnitType(val type: String) {
    LENGTH("Length"),
    WEIGHT("Weight"),
    VOLUME("Volume"),
    TEMPERATURE("Temperature"),
    AREA("Area"),
    SPEED("Speed"),
    TIME("Time"),
    DATA("Data"),
}