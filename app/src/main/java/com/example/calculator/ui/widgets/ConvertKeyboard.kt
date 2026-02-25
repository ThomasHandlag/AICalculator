package com.example.calculator.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Backspace
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.screen.KeyButon
import com.example.calculator.ui.screen.sample


@Preview(showBackground = true)
@Composable
fun ConvertKeyboard(
    onClick: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(6f)
        ) {
            items(sample.size, key = { sample[it] }) { index ->
                KeyButon(
                    background = Color(0xFFEDEDED),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onClick(sample[index])
                    }
                ) {
                    Text(
                        text = sample[index],
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 30.sp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.weight(1.9f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            KeyButon(
                onClick = {
                    onClick("Del")
                },
                background = Color(0xFFDDE3F3)
            ) {
                Icon(
                    Icons.AutoMirrored.Rounded.Backspace,
                    contentDescription = "Delete",
                )
            }
            KeyButon(
                onClick = {
                    onClick("C")
                },
                background = Color(0xFFDDE3F3)
            ) {
                Text(
                    text = "C",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 30.sp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(.5f)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFF1E5CC6))
                    .clickable(
                        onClick = {
                            onClick("=")
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.SwapVert,
                    contentDescription = "Delete",
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }
        }
    }
}