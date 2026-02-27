package com.example.calculator.ui.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.ui.AppViewModelProvider
import com.example.calculator.ui.home.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onNavigate: () -> Unit = { },
    onPop: () -> Unit = { },
    canPop: Boolean = false,
    appViewModel: AppViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val language by appViewModel.language.collectAsState()
    val theme by appViewModel.theme.collectAsState()
    val openFeedbackDialog = remember { mutableStateOf(false) }
    val openThemeDialog = remember { mutableStateOf(false) }
    val openExitDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xFFF3F5F6),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF3F5F6)
                ),
                title = {
                    Text("App Settings")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (canPop) {
                            onPop()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    Modifier.padding(16.dp),
                ) {
                    Text("Personalization")
                    SettingItem(
                        onClick = {
                            onNavigate()
                        },
                        leading = {
                            Icon(Icons.Outlined.Language, contentDescription = "Back")
                        },
                        title = "Language",
                        subtitle = language.displayName,
                        trailing = {
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = "Back",
                                modifier = Modifier.size(16.dp)
                            )
                        })
                    HorizontalDivider()
                    SettingItem(
                        leading = {
                            Icon(Icons.Outlined.DarkMode, contentDescription = "Back")
                        },
                        title = "App Theme",
                        subtitle = theme.displayName,
                        trailing = {
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = "Back",
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        onClick = {
                            openThemeDialog.value = true
                        })
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    Modifier.padding(16.dp),
                ) {
                    Text("Communicate")
                    SettingItem(
                        leading = {
                            Icon(Icons.AutoMirrored.Outlined.Chat, contentDescription = "Back")
                        },
                        title = "Feedback",
                        subtitle = "Share your feedback",
                        trailing = {
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = "Back",
                                modifier = Modifier.size(16.dp)
                            )
                        })
                    HorizontalDivider()
                    SettingItem(
                        leading = {
                            Icon(Icons.Outlined.PrivacyTip, contentDescription = "Privacy")
                        },
                        title = "Privacy Policy",
                        subtitle = "See how we protect your data",
                        trailing = {
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = "Back",
                                modifier = Modifier.size(16.dp)
                            )
                        })
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    Modifier.padding(16.dp),
                ) {
                    Text("Others")
                    SettingItem(
                        leading = {
                            Icon(Icons.Outlined.Share, contentDescription = "Back")
                        },
                        title = "Share App",
                        subtitle = "Share with your friends",
                        trailing = {
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = "Back",
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Hello! This is a shared message from my Jetpack Compose app."
                                )
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Share via")
                            context.startActivity(shareIntent)
                        }
                    )
                    HorizontalDivider()
                    SettingItem(
                        leading = {
                            Icon(Icons.Outlined.Star, contentDescription = "Privacy")
                        },
                        title = "Rate Us",
                        subtitle = "Give us your rating",
                        trailing = {
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = "Back",
                                modifier = Modifier.size(16.dp)
                            )
                        }, onClick = {
                            openFeedbackDialog.value = true
                        })
                    HorizontalDivider()
                    SettingItem(
                        leading = {
                            Icon(Icons.Outlined.Info, contentDescription = "Privacy")
                        },
                        title = "About Us",
                        subtitle = "Know more about us",
                        trailing = {
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = "Back",
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        {
                            val url = "https://app.bustify.dev"
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                // Ensure the intent can be handled
                                if (intent.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "No browser found to open URL",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } catch (e: ActivityNotFoundException) {
                                Toast.makeText(context, "Unable to open URL", Toast.LENGTH_SHORT)
                                    .show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    )
                }
            }

            Text(
                "App version ${"0.0.1"}",
                color = Color(0xFF565F66),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
            )
        }

        when {
            openFeedbackDialog.value -> FeedbackDialog(
                onDismissRequest = {
                    openFeedbackDialog.value = false
                }
            )

            openThemeDialog.value -> ThemePickerDialog(
                currentMode = theme,
                onDismissRequest = {
                    openThemeDialog.value = false
                }, onApply = {
                    appViewModel.setTheme(it)
                    openThemeDialog.value = false
                }
            )

            openExitDialog.value -> ExitDialog(onDismissRequest = {
                openExitDialog.value = false
            })
        }
    }
}


@Composable
fun SettingItem(
    leading: @Composable () -> Unit = { },
    title: String = "",
    subtitle: String = "",
    trailing: @Composable () -> Unit = { },
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable(
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(14.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column { leading() }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(title, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        subtitle,
                        color = Color(0xFF565F66),
                        style = MaterialTheme.typography.bodySmall.copy(
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
            Column(verticalArrangement = Arrangement.Center) { trailing() }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SettingItemPreview() {
    SettingItem(
        leading = {
            Icon(Icons.Outlined.Language, contentDescription = "Back")
        },
        title = "Title",
        subtitle = "Subtitle",
        trailing = {
            Icon(
                Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = "Back",
                modifier = Modifier.size(16.dp)
            )
        }
    )
}
