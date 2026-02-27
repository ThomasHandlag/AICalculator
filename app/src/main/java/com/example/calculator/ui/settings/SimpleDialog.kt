package com.example.calculator.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.calculator.enums.AppThemeMode
import kotlin.system.exitProcess

@Preview
@Composable
fun SimpleDialogPreview() {
    SimpleDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = "Feedback",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }, actions = {
            TextButton(
                onClick = {}
            ) {
                Text("Cancel")
            }
            TextButton(
                onClick = {}
            ) {
                Text("Submit", color = Color(0xFF2A8CFC))
            }
        }) {
        Text(
            "Are you sure to exit this application?",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun SimpleDialog(
    onDismissRequest: () -> Unit,
    title: @Composable () -> Unit,
    actions: @Composable (RowScope.() -> Unit),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF0F7FD)
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                title()
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    content()
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    actions()
                }
            }
        }
    }
}

@Preview
@Composable
fun FeedbackDialog(
    onDismissRequest: () -> Unit = {}
) {
    var rate by rememberSaveable { mutableStateOf(0) }
    SimpleDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = "Feedback",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }, actions = {
            TextButton(
                onClick = {}
            ) {
                Text("Cancel")
            }
            TextButton(
                onClick = {}
            ) {
                Text("Submit", color = Color(0xFF2A8CFC))
            }
        }) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                "If you enjoy using Ai Calculator App " +
                        "please take a moment to rate it.",
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= rate) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier
                            .padding(4.dp)
                            .size(38.dp)
                            .clickable { rate = i }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ExitDialog(
    onDismissRequest: () -> Unit = {},
) {
    SimpleDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Exit",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }, actions = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text("Cancel")
            }
            TextButton(
                onClick = {
                    exitProcess(0)
                }
            ) {
                Text("Quit", color = Color(0xFF2A8CFC))
            }
        }) {
        Text(
            "Are you sure to exit this application?",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
fun ThemePickerDialog(
    currentMode: AppThemeMode = AppThemeMode.SYSTEM,
    onApply: (AppThemeMode) -> Unit = { },
    onDismissRequest: () -> Unit = {}
) {
    val selectedMode = rememberSaveable { mutableStateOf(currentMode) }
    SimpleDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            Text(
                text = "App Theme",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }, actions = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
            TextButton(
                onClick = {
                    onApply(selectedMode.value)
                }
            ) {
                Text("Apply", color = Color(0xFF2A8CFC))
            }
        }) {
        Column(
            Modifier
                .selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AppThemeMode.entries.forEach {
                Row(
                    modifier = Modifier.selectable(
                        selected = selectedMode.value.code == currentMode.code,
                        onClick = {
                            selectedMode.value = it
                        },
                        role = Role.RadioButton
                    )
                ) {
                    RadioButton(
                        selected = selectedMode.value.code == it.code,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF1977FB),
                        )
                    )

                    Text(
                        text = "${it.displayName} Mode",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}