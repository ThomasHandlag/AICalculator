package com.example.calculator.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.R
import com.example.calculator.ui.widgets.UnitSelector

@Composable
fun UnitTypeItem(
    resourceId: Int,
    title: String,
) {
    Box(
        modifier = Modifier.border(
            width = 1.dp, color = Color(0xFFC9D5EF),
            shape = RoundedCornerShape(16.dp)
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(resourceId),
                contentDescription = title,
                modifier = Modifier.size(25.dp)
            )
            Text(title, style = MaterialTheme.typography.labelSmall.copy(fontSize = 18.sp))
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UnitConverter() {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back Button"
                    )
                },

                title = {
                    Text("Unit Converter")
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    UnitTypeItem(
                        resourceId = R.drawable.length,
                        title = "Length"
                    )
                }
                item {
                    UnitTypeItem(
                        resourceId = R.drawable.mass,
                        title = "Mass"
                    )
                }
                item {
                    UnitTypeItem(
                        resourceId = R.drawable.area,
                        title = "Area"
                    )
                }
                item {
                    UnitTypeItem(
                        resourceId = R.drawable.volumn,
                        title = "Volume"
                    )
                }
                item {
                    UnitTypeItem(
                        resourceId = R.drawable.time,
                        title = "Time"
                    )
                }
                item {
                    UnitTypeItem(
                        resourceId = R.drawable.data,
                        title = "Data"
                    )
                }

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    onClick = {}
                )
            ) {
                Text("From")
                Icon(Icons.Rounded.ExpandMore, contentDescription = "Select Unit")
            }
            Box() {
                Text("100")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    onClick = {}
                )
            ) {
                Text("From")
                Icon(Icons.Rounded.ExpandMore, contentDescription = "Select Unit")
            }
            Box() {
                Text("100")
            }

            if (false) {
                UnitSelector()
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun UnitTypeItemPreview() {
    UnitTypeItem(
        resourceId = R.drawable.length,
        title = "Length"
    )
}