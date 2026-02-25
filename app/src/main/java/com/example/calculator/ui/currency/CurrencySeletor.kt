package com.example.calculator.ui.currency

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.widgets.SelectorItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelector(
    onSelect: (Currency?) -> Unit = {},
    show: Boolean = true,
    sheetState: SheetState,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    if (show) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onSelect(null)
            },
            dragHandle = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp, start = 25.dp, end = 25.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Choose Currency",
                        fontWeight = FontWeight.Bold,
                        fontFamily = MaterialTheme.typography.labelSmall.fontFamily,
                        fontSize = 20.sp
                    )

                    Icon(
                        Icons.Rounded.Close,
                        contentDescription = "",
                        modifier = Modifier.clickable(
                            onClick = {
                                onSelect(null)
                            }
                        ))

                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp)
            ) {
                SearchField(
                    value = searchQuery,
                    onChange = {
                        searchQuery = it
                    }
                )
                Text(
                    "Currencies",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = MaterialTheme.typography.labelSmall.fontFamily,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Currency.entries.filter { it.displayName.contains(Regex.fromLiteral(searchQuery)) }
                        .forEach { currency ->
                            item {
                                SelectorItem(
                                    leading = {
                                        if (currency.flagRsId != null) {
                                            Image(
                                                painterResource(currency.flagRsId),
                                                modifier = Modifier.size(30.dp, height = 25.dp),
                                                contentDescription = currency.code,
                                            )
                                        }
                                    },
                                    unit = currency,
                                    onSelect = { onSelect(it) },
                                    title = { Text(it.name) },
                                    label = { Text(it.displayName, color = Color.Gray) }
                                )
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun SearchField(
    value: String = "",
    onChange: (String) -> Unit = { },
) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            leadingIcon =
                {
                    Icon(Icons.Rounded.Search, contentDescription = "Search")
                },
            value = value,
            onValueChange = onChange,
            placeholder = { Text("Search") },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                unfocusedContainerColor = Color(0xFFF0F0F0),
                focusedContainerColor = Color(0xFFF0F0F0),
            ),
            modifier = Modifier.fillMaxWidth(if (value.isNotEmpty()) 0.8f else 1f)
        )
        if (value.isNotEmpty()) {
            Text(
                "Cancel", modifier = Modifier
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CurrencySelectorPreview() {
    val sheetState = rememberModalBottomSheetState { true }
    CurrencySelector(sheetState = sheetState, show = true)
}