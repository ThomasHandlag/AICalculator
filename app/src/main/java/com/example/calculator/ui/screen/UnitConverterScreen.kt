package com.example.calculator.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Backspace
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.R
import com.example.calculator.ui.AppViewModelProvider
import com.example.calculator.ui.viewmodel.UnitViewModel
import com.example.calculator.ui.widgets.CalUnitType
import com.example.calculator.ui.widgets.UnitSelector

@Composable
fun UnitTypeItem(
    resourceId: Int,
    title: String,
    selected: Boolean = false,
    onClick: () -> Unit = { }
) {
    Surface(
        modifier = Modifier,
        shape = RoundedCornerShape(16.dp),
        onClick = {
            onClick()
        },
        border = BorderStroke(
            width = 1.dp, color = Color(0xFFC9D5EF),
        ),
        color = if (selected) Color(0xAAC9D5EF) else Color.Transparent,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(8.dp),
        ) {
            Image(
                painter = painterResource(resourceId),
                contentDescription = title,
                modifier = Modifier.size(25.dp)
            )
            Text(title, style = MaterialTheme.typography.labelSmall.copy(fontSize = 18.sp))
        }
    }
}

//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UnitConverterScreen(
    converterViewModel: UnitViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavigate: () -> Unit = { },
    canPop: Boolean = false
) {
    val fromUnit by converterViewModel.fromUnit.collectAsState()
    val toUnit by converterViewModel.toUnit.collectAsState()
    val inputValue by converterViewModel.inputValue.collectAsState()
    val result by converterViewModel.resultValue.collectAsState()
    val convertType by converterViewModel.convertType.collectAsState()

    var showUnitSelector by remember { mutableStateOf(false) }
    var selectFor by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (canPop) {
                                onNavigate()
                            }
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                },

                title = {
                    Text("Unit Converter")
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        UnitTypeItem(
                            resourceId = R.drawable.length,
                            title = "Length",
                            selected = convertType == CalUnitType.LENGTH,
                            onClick = {
                                converterViewModel.setConvertType(CalUnitType.LENGTH)
                            }
                        )
                    }
                    item {
                        UnitTypeItem(
                            resourceId = R.drawable.mass,
                            title = "Mass",
                            selected = convertType == CalUnitType.WEIGHT,
                            onClick = {
                                converterViewModel.setConvertType(CalUnitType.WEIGHT)
                            }
                        )
                    }
                    item {
                        UnitTypeItem(
                            resourceId = R.drawable.area,
                            title = "Area",
                            selected = convertType == CalUnitType.AREA,
                            onClick = {
                                converterViewModel.setConvertType(CalUnitType.AREA)
                            }
                        )
                    }
                    item {
                        UnitTypeItem(
                            resourceId = R.drawable.volumn,
                            title = "Volume",
                            selected = convertType == CalUnitType.VOLUME,
                            onClick = {
                                converterViewModel.setConvertType(CalUnitType.VOLUME)
                            }
                        )
                    }
                    item {
                        UnitTypeItem(
                            resourceId = R.drawable.time,
                            title = "Time",
                            selected = convertType == CalUnitType.TIME,
                            onClick = {
                                converterViewModel.setConvertType(CalUnitType.TIME)
                            }
                        )
                    }
                    item {
                        UnitTypeItem(
                            resourceId = R.drawable.data,
                            title = "Data",
                            selected = convertType == CalUnitType.DATA,
                            onClick = {
                                converterViewModel.setConvertType(CalUnitType.DATA)
                            }
                        )
                    }

                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(
                            onClick = {
                                selectFor = true
                                showUnitSelector = true
                            }
                        )
                    ) {
                        Text("${fromUnit.unit} (${fromUnit.label})")
                        Icon(Icons.Rounded.ArrowDropDown, contentDescription = "Select Unit")
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFC9D5EF),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            inputValue, modifier = Modifier
                                .padding(17.dp)
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(
                            onClick = {
                                selectFor = false
                                showUnitSelector = true
                            }
                        )
                    ) {
                        Text("${toUnit.unit} (${toUnit.label})")
                        Icon(Icons.Rounded.ArrowDropDown, contentDescription = "Select Unit")
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFC9D5EF),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            result, modifier = Modifier
                                .padding(17.dp)
                        )
                    }
                }
            }

            ConvertKeyboard(
                onClick = {
                    when (it) {
                        "+/-" -> {
                            converterViewModel.setInputValue(
                                if (inputValue.startsWith("-")) inputValue.drop(1) else "-$inputValue"
                            )
                        }

                        "C" -> {
                            converterViewModel.setInputValue("")
                        }

                        "Del" -> {
                            converterViewModel.setInputValue(
                                if (inputValue.isNotEmpty()) inputValue.dropLast(1) else ""
                            )
                        }

                        "=" -> {
                            converterViewModel.calResultValue()
                        }

                        else -> {
                            converterViewModel.setInputValue(inputValue + it)
                        }
                    }
                }
            )

            UnitSelector(
                unitType = convertType, onSelect = {
                    if (it == null) {
                        showUnitSelector = false
                        return@UnitSelector
                    }

                    if (selectFor) {
                        converterViewModel.setFromUnit(it)
                    } else {
                        converterViewModel.setToUnit(it)
                    }
                    showUnitSelector = false
                }, sheetState = bottomSheetState, show = showUnitSelector,
                selectedUnit = if (selectFor) fromUnit else toUnit,
                selectFor = selectFor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitTypeItemPreview() {
    UnitTypeItem(
        resourceId = R.drawable.length,
        title = "Length",
        selected = true
    )
}

@Composable
fun KeyButon(
    modifier: Modifier = Modifier,
    background: Color,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(background)
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

val sample = listOf(
    "7", "8", "9",
    "4", "5", "6",
    "1", "2", "3",
    "+/-", ".", "0"
)

@Preview(showBackground = true)
@Composable
fun ConvertKeyboard(
    onClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(6f)
        ) {
            items(sample.size, key = { sample[it] }) { index ->
                KeyButon(
                    background = Color(0xFFEDEDED),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onClick(sample[index])
                    }
                ) {
                    Text(
                        text = sample[index],
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }
        }

        Column(
            modifier = Modifier.weight(1.9f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            KeyButon(
                onClick = {
                    onClick("Del")
                },
                background = Color(0xFFDDE3F3)
            ) {
                Icon(
                    Icons.Rounded.Backspace,
                    contentDescription = "Delete",
                )
            }
            KeyButon(
                onClick = {
                    onClick("C")
                },
                background = Color(0xFFDDE3F3)
            ) {
                Text(
                    text = "C",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(.5f)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFF1E5CC6))
                    .clickable(
                        onClick = {
                            onClick("=")
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.SwapVert,
                    contentDescription = "Delete",
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }
        }
    }
}