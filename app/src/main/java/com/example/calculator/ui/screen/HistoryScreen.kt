package com.example.calculator.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.ui.AppViewModelProvider
import com.example.calculator.ui.viewmodel.CalHistoryData
import com.example.calculator.ui.viewmodel.HistoryViewModel
import com.example.calculator.ui.widgets.HistoryDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    props: ScreenProps = ScreenProps(
        onNavigate = {},
        title = "History"
    ),
    onNavigateToCal: (id: Int) -> Unit = { },
) {
    val historyList by historyViewModel.historyList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("History", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }, navigationIcon = {
                IconButton(
                    onClick = { props.onPop() }) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "")
                }
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                historyList.forEach { item ->
                    item {
                        HistoryItem(item = item, onNavigateToCal, copyToClipboard = {
                            result, clipboard ->
                            historyViewModel.copyToClipboard(result, clipboard)
                        }, onDelete = { id ->
                            historyViewModel.deleteHistoryItem(id)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItem(
    item: CalHistoryData,
    onNavigateToCal: (id: Int) -> Unit = { },
    onDelete: (id: Int) -> Unit = { },
    copyToClipboard: (result: String, clipboard: Clipboard) -> Unit = { _, _ -> },
) {
    val (showDetails, setShowDetails) = remember {
        mutableStateOf(false)
    }
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFAFCFBFC),
        modifier = Modifier.border(
            width = 1.dp,
            color = Color(0xFFE0E0E0),
            shape = RoundedCornerShape(16.dp)
        ),
        onClick = {
            setShowDetails(true)
        }
    ) {
        val clipboardManager = LocalClipboard.current
        HistoryDetail(
            item, onNavigate = {
                onNavigateToCal(item.id)
            },
            onClose = {
                setShowDetails(false)
            },
            isShow = showDetails,
            onCopy = {
                copyToClipboard(item.result, clipboardManager)
            }, onDelete = {
                setShowDetails(false)
                onDelete(item.id)
            })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    item.expression,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = Color.Gray
                )
                Text(
                    item.result,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    color = Color.Black
                )
            }
            Icon(
                contentDescription = "Chevron Right",
                modifier = Modifier.size(12.dp),
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
            )
        }
    }
}

@Preview
@Composable
fun HistoryItemPreview() {
    HistoryItem(
        item = CalHistoryData(
            id = 1,
            expression = "23 + 45 × 6",
            result = "293",
            timestamp = java.util.Date()
        )
    )
}