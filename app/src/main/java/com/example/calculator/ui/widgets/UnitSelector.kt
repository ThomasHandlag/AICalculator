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
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
@Preview()
fun UnitSelector(
    unitType: CalUnitType = CalUnitType.LENGTH
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
        CalUnitType.TEMPERATURE -> listOf(
            TemperatureUnit.C,
            TemperatureUnit.F,
            TemperatureUnit.K
        )
        CalUnitType.AREA -> listOf(
            AreaUnit.SQM,
            AreaUnit.SQKM,
            AreaUnit.SQFT,
            AreaUnit.ACRE
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
    }

    Surface(color = Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Choose Unit", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                IconButton(
                    onClick = {}) {
                    Icon(Icons.Rounded.Close, contentDescription = "")
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (unit in unitList) {
                    item {
                        UnitItem(
                            unit = unit,
                            selected = unit.unit == "cm",
                            onSelect = {}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UnitItem(
    unit: CalUnit, selected: Boolean = false, onSelect: (CalUnit) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (selected) Color.Blue else Color(0xFAF3F5F6),
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    unit.unit,
                    color = if (selected) Color.White else Color.Black,
                )
                Text(unit.label, color = Color(0xFA66707E))
            }
            IconButton(onClick = {

            }) {
                Icon(
                    Icons.Rounded.ArrowForwardIos,
                    contentDescription = "",
                    tint = Color(0xFA66707E)
                )
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
    object ML : CalUnit("ml", "Milliliter")
    object L : CalUnit("l", "Liter")
    object GAL : CalUnit("gal", "Gallon")
    object PT : CalUnit("pt", "Pint")
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
    object ACRE : CalUnit(
        "acre",
        "Acre"
    )
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