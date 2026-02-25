package com.example.calculator.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.calculator.R


val customFont = FontFamily(
    Font(R.font.roboto_var_wdth_wght, FontWeight.Normal),
    Font(R.font.roboto_italic_wdth_wght, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = customFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    ),
    bodyMedium = TextStyle(
        fontFamily = customFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    ),
    bodySmall = TextStyle(
        fontFamily = customFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    labelSmall = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    displayLarge = TextStyle(
        fontFamily = customFont,
        fontSize = 55.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 6.5.sp
    ),
    displayMedium = TextStyle(
        fontFamily = customFont,
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium
    ),
    displaySmall = TextStyle(
        fontFamily = customFont,
        fontSize = 26.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    titleLarge = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = customFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
    ),
)

