package com.example.calculator.ui.ai

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Person4
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.ui.AppViewModelProvider

@Composable
fun AiChatPage(
    genAIViewModel: GenAIViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val promptText = rememberSaveable { mutableStateOf("") }
    val chatMessages = genAIViewModel.chatMessages.collectAsState()
    val context = LocalContext.current
    val aiThinking = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            chatMessages.value.forEach {
                item {
                    ChatBubble(
                        message = it.message,
                        isUser = it.isUser
                    )
                }
            }
            if (aiThinking.value) {
                item {
                    Row(
                        Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Brush.radialGradient(
                                        center = Offset(120f, 50f),
                                        radius = 100f,
                                        tileMode = TileMode.Mirror,
                                        colors = listOf(
                                            Color.Blue, Color.Cyan, Color.Blue
                                        )
                                    ), shape = RoundedCornerShape(50)
                                )
                                .padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier.blur(
                                    radius = 100.dp
                                )
                            )
                            Icon(
                                Icons.Rounded.AutoAwesome,
                                contentDescription = "AI",
                                tint = Color.White,
                            )
                        }
                        Text(
                            "Running...",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF0B57D0),
                                fontWeight = FontWeight.Medium,
                                lineHeight = 16.sp,
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(Color(0xFFF1F5F9), shape = RoundedCornerShape(100.dp))
                .border(
                    border = BorderStroke(
                        width = 1.dp, color = Color(0xFFE3EAF0)
                    ), shape = RoundedCornerShape(
                        100.dp
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            OutlinedTextField(
                value = promptText.value,
                leadingIcon = {
                    Icon(
                        Icons.Rounded.AutoAwesome,
                        contentDescription = "AI",
                        modifier = Modifier
                            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                            .drawWithCache {
                                // Create a linear gradient brush
                                val gradient = Brush.linearGradient(
                                    colors = listOf(
                                        Color.Blue,
                                        Color.Cyan,
                                        Color.Blue,
                                        Color.Cyan,
                                        Color.Blue
                                    ),
                                    start = Offset.Zero,
                                    end = Offset(size.width, size.height)
                                )
                                onDrawWithContent {
                                    // Draw the icon's alpha mask with the gradient
                                    drawContent()
                                    drawRect(
                                        brush = gradient,
                                        blendMode = BlendMode.SrcAtop
                                    )
                                }
                            }
                    )
                },
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    center = Offset(120f, 50f),
                                    radius = 100f,
                                    tileMode = TileMode.Mirror,
                                    colors = listOf(
                                        Color.Blue, Color.Cyan, Color.Blue
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = {
                            aiThinking.value = true
                            genAIViewModel.generateResponse(promptText.value, onError = {
                                Toast.makeText(
                                    context,
                                    "Error: ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }, onSuccess = {
                                aiThinking.value = false
                            })
                            promptText.value = ""
                        }) {
                            Icon(
                                Icons.AutoMirrored.Filled.Send,
                                contentDescription = "User",
                                tint = Color.White,
                                modifier = Modifier
                                    .rotate(-45f)
                                    .size(16.dp)
                            )
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                ),
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .fillMaxWidth(),
                onValueChange = {
                    promptText.value = it
                },
                placeholder = {
                    Text(
                        "Ask any thing to your ai assistant...",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}

@Composable
fun ChatBubble(
    message: String,
    isUser: Boolean
) {
    Row(Modifier.padding(12.dp)) {
        Box(
            modifier = Modifier
                .background(
                    if (isUser)
                        Brush.horizontalGradient(
                            colors = listOf(Color.Blue, Color.Blue)
                        ) else Brush.radialGradient(
                        center = Offset(120f, 50f),
                        radius = 100f,
                        tileMode = TileMode.Mirror,
                        colors = listOf(
                            Color.Blue, Color.Cyan, Color.Blue
                        )
                    ), shape = RoundedCornerShape(50)
                )
                .padding(16.dp)
        ) {
            if (isUser) {
                Icon(Icons.Rounded.Person4, contentDescription = "User", tint = Color.White)
            } else {
                Box(
                    modifier = Modifier.blur(
                        radius = 100.dp
                    )
                )
                Icon(
                    Icons.Rounded.AutoAwesome,
                    contentDescription = "AI",
                    tint = Color.White,
                )
            }
        }
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                if (isUser) "User" else "AI",
                style = MaterialTheme.typography.bodySmall.copy(
                    lineHeight = 16.sp, fontWeight = FontWeight.Medium,
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = message,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}
