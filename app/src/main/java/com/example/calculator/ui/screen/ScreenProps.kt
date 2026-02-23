package com.example.calculator.ui.screen

import androidx.navigation.NavController

data class ScreenProps(val title: String, val onNavigate: () -> Unit = {}, val onPop: ()-> Unit = {})