package com.example.calculator.enums

import com.example.calculator.enums.AppLanguage.SYSTEM_DEFAULT

enum class AppLanguage(val code: String, val displayName: String) {
    SYSTEM_DEFAULT("system", "System Default"),
    ENGLISH("en", "English"),
    CHINESE("zh", "Chinese"),
    HINDI("hi", "Hindi"),
    FRENCH("fr", "French"),
    ARABIC("ar", "Arabic"),
    SPANISH("es", "Spanish"),
    BENGALI("bn", "Bengali"),
    ITALIAN("it", "Italian"),
    RUSSIAN("ru", "Russian"),
    PORTUGUESE("pt", "Portuguese");
}

fun valueOfCode(code: String): AppLanguage {
    return AppLanguage.entries.firstOrNull { it.code == code } ?: SYSTEM_DEFAULT
}