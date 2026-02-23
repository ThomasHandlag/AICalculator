package com.example.calculator.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.enums.scienceSymbols
import com.example.calculator.enums.symbols
import com.example.calculator.ui.AppViewModelProvider
import com.example.calculator.ui.theme.Typography
import com.example.calculator.ui.viewmodel.BasicViewModel
import com.example.calculator.ui.widgets.CalButton
import com.example.calculator.ui.widgets.CalInput

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun BasicScreen(
    basicViewModel: BasicViewModel = viewModel(factory = AppViewModelProvider.Factory),
    props: ScreenProps = ScreenProps(
        title = "",
        onNavigate = { }
    ),
    initId: Int? = null,
    canPop: Boolean = false,
) {
    val input by basicViewModel.expStr.collectAsState()
    val result by basicViewModel.result.collectAsState()
    val mode by basicViewModel.mode.collectAsState()
    val scrollState = rememberScrollState()
    val clipboardManager = LocalClipboard.current
    val expression = basicViewModel.expressionListState.collectAsState()

    if (initId != null) {
        basicViewModel.initState(initId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (canPop)
                        IconButton(
                            onClick = {
                                props.onPop()
                            }) {
                            Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "")
                        }
                },
                title = {},
                colors = TopAppBarColors(
                    Color(0xFFE3E5E6),
                    scrolledContainerColor = Color(0xFFE3E5E6),
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black,
                    subtitleContentColor = Color.Black
                ),
                modifier = Modifier.background(Color(0xFFE3E5E6)),
                actions = {
                    IconButton(
                        onClick = {}) {
                        Icon(Icons.Rounded.AutoAwesome, contentDescription = "")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                color = Color(0xFFE3E5E6),
                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    CalInput(
                        value = expression.value,
                        onPaste = {
                            basicViewModel.pasteFromClipboard(clipboardManager)
                        },
                        basicViewModel = basicViewModel
                    )
                    Text(
                        result,
                        fontSize = 40.sp,
                        color = Color(0xFFAAAAAA),
                        modifier = Modifier
                            .height(60.dp)
                            .horizontalScroll(scrollState)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        TextButton(
                            onClick = {
                                basicViewModel.setMode()
                            },
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.SwapHoriz,
                                    contentDescription = ""
                                )
                                Text(
                                    if (mode) "Science" else "Simple",
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            }
                        }
                        VerticalDivider(modifier = Modifier.height(40.dp))
                        TextButton(
                            onClick = {
                                props.onNavigate()
                            }) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.History,
                                    contentDescription = ""
                                )
                                Text("History", color = Color.Black, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
            CalKeyboard(
                mode = mode,
                onClick = { token ->
                    when (token) {
                        "AC" -> {
                            basicViewModel.clear()
                        }

                        "" -> {
                            if (input.isNotEmpty()) {
                                basicViewModel.popToken()
                            }
                        }

                        "=" -> basicViewModel.calculate()

                        else -> {
                            val newValue = if (token in scienceSymbols.filter {
                                    it !in listOf(
                                        "π",
                                        "Φ",
                                        "e"
                                    )
                                }) "$token(" else token

                            basicViewModel.pushTokens(
                                listOf(
                                    newValue,
                                )
                            )

                            if (token in scienceSymbols.filter {
                                    it !in listOf(
                                        "π",
                                        "Φ",
                                        "e"
                                    )
                                }) {
                                basicViewModel.pushTokens(listOf(" ", ")"))
                            }
                        }
                    }
                },
            )
        }
    }
}

@Composable
fun CalKeyboard(
    onClick: (symbol: String) -> Unit,
    padding: PaddingValues = PaddingValues(16.dp),
    mode: Boolean = false,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        contentPadding = padding,
        modifier = Modifier.fillMaxSize()
    ) {
        if (mode)
            scienceSymbols.forEach { scienceSymbol ->
                item {
                    CalButton(
                        symbol = scienceSymbol,
                        onClick = { value ->
                            onClick(value)
                        }, bgColor = Color(0xFFE5EDFF), aspectRatio = if (mode) 1.6f else 1f
                    ) {
                        Text(
                            scienceSymbol,
                            fontSize = Typography.labelSmall.fontSize,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp),
                        )
                    }
                }
            }
        symbols.forEach { symbol ->
            val color: Color = when (symbol) {
                "AC" -> Color(0xFFE8DBFC)
                "%" -> Color(0xFFE5EDFF)
                "x" -> Color(0xFFE5EDFF)
                "+" -> Color(0xFFE5EDFF)
                "-" -> Color(0xFFE5EDFF)
                "÷" -> Color(0xFFE5EDFF)
                "( )" -> Color(0xFFE5EDFF)
                else -> Color(0xFFE3E5E6)
            }
            item {
                CalButton(
                    symbol = symbol,
                    onClick = { value ->
                        onClick(value)
                    }, bgColor = color, aspectRatio = if (mode) 1.6f else 1f
                ) {
                    Text(
                        symbol,
                        fontSize = Typography.labelSmall.fontSize,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp),
                    )
                }
            }
        }
        item {
            CalButton(
                symbol = "",
                onClick = { value ->
                    onClick(value)
                }, aspectRatio = if (mode) 1.6f else 1f
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.Backspace,
                        contentDescription = ""
                    )
                }
            }
        }
        item {
            CalButton(
                symbol = "=",
                aspectRatio = if (mode) 1.6f else 1f,
                onClick = { value ->
                    onClick(value)
                },
                bgColor = Color(0xFF0B57D0),
            ) {
                Text(
                    "=",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp),
                    color = Color.White
                )
            }
        }
    }

}

