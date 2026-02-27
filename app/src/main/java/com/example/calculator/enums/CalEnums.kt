package com.example.calculator.enums

val symbols: List<String> = listOf(
    "AC", "( )", "%", "÷", "7", "8", "9", "x", "4", "5", "6", "-", "1", "2", "3", "+", "0", "."
)

val scienceSymbols: List<String> = listOf(
    "Φ", "e", "log", "ln",
    "sin", "cos", "tan", "π", "e", "∛", "√", "^"
)

enum class AppThemeMode(val code: String, val displayName: String) {
    SYSTEM("system", "System"),
    LIGHT("light", "Light"),
    DARK("dark", "Dark")
}