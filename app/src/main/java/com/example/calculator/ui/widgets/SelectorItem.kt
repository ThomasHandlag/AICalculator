package com.example.calculator.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> SelectorItem(
    unit: T, selected: Boolean = false,
    onSelect: (T) -> Unit,
    title: @Composable (T) -> Unit = {},
    label: @Composable (T) -> Unit = {},
    leading: @Composable () -> Unit = { },
    trailing: @Composable () -> Unit = {
        IconButton(
            onClick = {
            }
        ) {
            Icon(
                Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = "",
                modifier = Modifier.size(16.dp),
                tint = Color(0xFA66707E)
            )
        }
    }
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (selected) Color.Blue else Color(0xFAF3F5F6),
        modifier = Modifier,
        onClick = {
            onSelect(unit)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                leading()
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    title(unit)
                    label(unit)
                }
            }
            trailing()
        }
    }
}
