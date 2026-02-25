package com.example.calculator.ui.currency

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.ui.AppViewModelProvider
import com.example.calculator.ui.widgets.ConvertKeyboard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(
    onNavigate: () -> Unit = { },
    canPop: Boolean = false,
    currencyViewModel: CurrencyViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val from by currencyViewModel.fromCurrency.collectAsState()
    val to by currencyViewModel.toCurrency.collectAsState()
    val amount by currencyViewModel.amount.collectAsState()
    val result by currencyViewModel.result.collectAsState()
    var show by rememberSaveable { mutableStateOf(false) }
    var selectFor by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState { true }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    "Currency Converter",
                )
            }, navigationIcon = {
                IconButton(
                    onClick = if (canPop) onNavigate else ({})
                ) {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.padding(4.dp)
                    )
                }
            })
        },

        ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    selectFor = false
                                    show = true
                                })
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(from.flagRsId!!),
                            contentDescription = "From Currency",
                            modifier = Modifier.size(24.dp)
                        )
                        Row {
                            Text(
                                from.name,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                )
                            )
                            Text(
                                " (${from.displayName})",
                                style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Start)
                            )
                        }
                        Icon(Icons.Rounded.ArrowDropDown, contentDescription = "Select Unit")
                    }
                    Box(
                        modifier = Modifier
                            .height(56.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFF0B57D0),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(Color(0xFFF3F7FF)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(17.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                amount,
                                modifier = Modifier
                                    .fillMaxWidth(.99f),
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.End
                            )
                            Box(
                                Modifier
                                    .width(2.dp)
                                    .height(18.dp)
                                    .background(Color.Blue)
                            )
                        }
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    selectFor = true
                                    show = true
                                })
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(to.flagRsId!!),
                            contentDescription = "To Currency",
                            modifier = Modifier.size(24.dp)
                        )
                        Row {
                            Text(
                                to.name,
                                modifier = Modifier.padding(horizontal = 8.dp),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                            Text(
                                "(${to.displayName})",
                                style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Start),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                        Icon(Icons.Rounded.ArrowDropDown, contentDescription = "Select Unit")
                    }
                    Box(
                        modifier = Modifier
                            .height(56.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFC9D5EF),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(
                                Color(0xFFE8EFF7),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            result, modifier = Modifier
                                .padding(17.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.End
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        "1 ${from.code} = 0.85 ${to.code}",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Rate of ${"09:19, Aug 22, 2025"} ",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Icon(
                            Icons.Rounded.Refresh,
                            contentDescription = "Refresh Rate",
                            tint = Color(0xFF0B57D0),
                            modifier = Modifier
                                .clickable {

                                }
                        )
                    }
                }
            }

            ConvertKeyboard(
                onClick = {
                    when (it) {
                        "C" -> {
                            currencyViewModel.clear()
                        }

                        "Del" -> {
                            val current = currencyViewModel.amount.value
                            if (current.isNotEmpty()) {
                                currencyViewModel.setAmount(
                                    current.dropLast(1)
                                )
                            }
                        }

                        "=" -> currencyViewModel.convert()
                        "+/-" -> {
                            val current = currencyViewModel.amount.value
                            if (current.startsWith("-")) {
                                currencyViewModel.setAmount(current.substring(1))
                            } else {
                                currencyViewModel.setAmount("-$current")
                            }
                        }

                        else -> {
                            val current = currencyViewModel.amount.value
                            if (current.length < 15) {
                                currencyViewModel.setAmount(current + it)
                            }
                        }
                    }
                }
            )

            CurrencySelector(
                show = show,
                onSelect = {
                    if (it != null) {
                        if (from == it) {
                            currencyViewModel.setFromCurrency(to)
                            currencyViewModel.setToCurrency(from)
                        } else {
                            if (selectFor) {
                                currencyViewModel.setToCurrency(it)
                            } else {
                                currencyViewModel.setFromCurrency(it)
                            }
                        }
                    }
                    show = false
                },
                sheetState = sheetState
            )
        }
    }
}
