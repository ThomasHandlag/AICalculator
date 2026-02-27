package com.example.calculator.ui.ai

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.data.database.ChatHistory
import com.example.calculator.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiHistoryScreen(
    onPop: () -> Unit = { },
    onNavigate: (id: Int) -> Unit = { },
    genAIViewModel: GenAIViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val historyList = genAIViewModel.chatHistories.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("History", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }, navigationIcon = {
                IconButton(
                    onClick = { onPop() }) {
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
                historyList.value.forEach { item ->
                    item {
                        AiHistoryItem(
                            item = item,
                            onNavigate = {
                                onNavigate(it)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AiHistoryItem(
    item: ChatHistory = ChatHistory(0, "Title", "2024-01-01 12:00:00"),
    onNavigate: (id: Int) -> Unit = { }
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .border(
                1.dp,
                Color(0xFFD9DBDF),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                Color(0xFFFCFBFC),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(12.dp)
            .clickable {
                onNavigate(item.id)
            },
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium
            )
        )
        Text(
            text = item.date,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Normal,
                color = Color(0xFF595959)
            ),
            maxLines = 1
        )
    }
}