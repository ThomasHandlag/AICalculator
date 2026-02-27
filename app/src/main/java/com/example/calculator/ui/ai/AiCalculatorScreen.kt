package com.example.calculator.ui.ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.rounded.AvTimer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.R
import com.example.calculator.ui.AppViewModelProvider

enum class AiCalculatorMode() {
    Scan,
    Chat
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiCalculatorScreen(
    id: Int? = null,
    genAIViewModel: GenAIViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
    onNavigate: () -> Unit = {},
    onPop: () -> Unit = {}
) {
    if (id != null) {
        genAIViewModel.setId(id)
    }
    val mode =
        rememberSaveable {
            mutableStateOf(
                if (id != null) AiCalculatorMode.entries[1]
                else AiCalculatorMode.entries[0]
            )
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (mode.value) {
                            AiCalculatorMode.Scan -> "Ai Scan" // stringResource(R.string.app_name)
                            AiCalculatorMode.Chat -> stringResource(R.string.app_name)
                        }
                    )

                },
                actions = {
                    IconButton(
                        onClick = {
                            onNavigate()
                        }
                    ) {
                        Icon(Icons.Rounded.AvTimer, contentDescription = "History")
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onPop()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier =
                    Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BottomBarItem(
                        selected = mode.value == AiCalculatorMode.Scan,
                        onSelect = {
                            mode.value = AiCalculatorMode.Scan
                        }
                    )
                    BottomBarItem(
                        label = "Chat",
                        onSelect = {
                            mode.value = AiCalculatorMode.Chat
                        }
                    ) {
                        Icon(
                            Icons.Filled.ChatBubble,
                            contentDescription = "Chat",
                            tint = if (mode.value == AiCalculatorMode.Chat) Color.Blue else Color.Unspecified
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            when (mode.value) {
                AiCalculatorMode.Scan -> AiScanPage()
                AiCalculatorMode.Chat -> AiChatPage()
            }
        }
    }
}

@Composable
fun BottomBarItem(
    selected: Boolean = false,
    label: String = "Scan",
    onSelect: () -> Unit = {},
    icon: @Composable () -> Unit = {
        Icon(
            Icons.Filled.DocumentScanner,
            contentDescription = "Scan",
            tint = if (selected) Color.Blue else Color.Unspecified
        )
    },
) {
    Surface(
        onClick = onSelect,
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon()
            Text(
                text = label,
                modifier = Modifier.weight(1f),
                color = if (selected) Color.Blue else Color.Unspecified,
            )
        }
    }
}

@Preview
@Composable
fun AiCalculatorScreenPreview() {
    AiCalculatorScreen()
}

