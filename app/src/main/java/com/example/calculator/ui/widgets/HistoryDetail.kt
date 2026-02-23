package com.example.calculator.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
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
import com.example.calculator.ui.screen.HistoryItem
import com.example.calculator.ui.viewModel.CalHistoryData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertDateTimeString(time: Date): String {
    val formatter = SimpleDateFormat("hh:mm MMM dd, yyyy", Locale.getDefault())
    return formatter.format(time)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetail(
    data: CalHistoryData,
    onNavigate: () -> Unit = {},
    onCopy: () -> Unit = {},
    onDelete: () -> Unit = {},
    isShow: Boolean = false,
    onClose: () -> Unit = {},
) {
    val sheetState = rememberModalBottomSheetState()
    if (isShow)
        ModalBottomSheet(
            onDismissRequest = {
                onClose()
            },
            sheetState = sheetState,
            containerColor = Color.White,
            dragHandle = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        convertDateTimeString(data.timestamp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    IconButton(
                        onClick = {
                            onClose()
                        }) {
                        Icon(Icons.Rounded.Close, contentDescription = "")
                    }
                }
            }
        ) {
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HistoryItem(
                        item = data
                    )
                    HDetailAction(
                        label = "Insert Formular",
                        onClick = {
                            onNavigate()
                        },
                        icon = {
                            Icon(Icons.Outlined.AddBox, contentDescription = "Icon add")
                        })
                    HDetailAction(
                        label = "Replace",
                        icon = {
                            Icon(
                                Icons.Rounded.Autorenew,
                                contentDescription = "Icon Replace"
                            )
                        },
                        onClick = {
                            onNavigate()
                        }
                    )
                    HDetailAction(
                        label = "Copy Result",
                        icon = {
                            Icon(
                                Icons.Rounded.ContentCopy,
                                contentDescription = "Icon edit"
                            )
                        },
                        onClick = {
                            onCopy()
                        }
                    )
                    HDetailAction(
                        label = "Delete",
                        icon = {
                            Icon(
                                Icons.Outlined.Delete,
                                contentDescription = "Icon favorite"
                            )
                        },
                        onClick = {
                            onDelete()
                        }
                    )
                }
            }
        }
}

@Composable
fun HDetailAction(
    onClick: () -> Unit = {},
    label: String = "Action",
    icon: @Composable () -> Unit = {
        Icon(Icons.AutoMirrored.Rounded.Backspace, contentDescription = "Icon add")
    }
) {
    Box(
        modifier = Modifier.clickable(
            onClick = {
                onClick()
            }
        ),
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                icon()
                Text(label)
            }
            HorizontalDivider()
        }
    }
}

@Preview
@Composable
fun HistoryDetailPreview() {
    Scaffold { paddingValues ->
        Modifier.padding(paddingValues)
        HistoryDetail(
            data = CalHistoryData(
                id = 1,
                expression = "2 + 2",
                result = "4",
                timestamp = Date()
            )
        )
    }
}