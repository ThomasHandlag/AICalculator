package com.example.calculator.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CalButton(
    symbol: String = "Sample",
    bgColor: Color = Color(0xFFF3F5F6),
    onClick: (symbol: String) -> Unit = {},
    aspectRatio: Float = 1f,
    content: @Composable () -> Unit = {},
) {
    Surface(
        onClick = {
            onClick(symbol)
        },
        shape = RoundedCornerShape(50.dp),
        color = bgColor,
        modifier = Modifier.fillMaxWidth().aspectRatio(aspectRatio)
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }
}