package com.example.calculator.ui.currency

import com.example.calculator.R

enum class Currency(
    val symbol: String,
    val code: String,
    val displayName: String,
    val flagRsId: Int? = null
) {

    USD("$", "USD", "US Dollar", R.drawable.us),
    EUR("€", "EUR", "Euro", R.drawable.eu),
    GBP("£", "GBP", "British Pound", R.drawable.gbp),
    JPY("¥", "JPY", "Japanese Yen", R.drawable.jp),
    CNY("¥", "CNY", "Chinese Yuan", R.drawable.cn),
    INR("₹", "INR", "Indian Rupee", R.drawable.india),
    CHF("CHF", "CHF", "Swiss Franc", R.drawable.fr),
    HKD("HK$", "HKD", "Hong Kong Dollar", R.drawable.hk),
    ZAR("R", "ZAR", "South African Rand", R.drawable.bd),
    AFN("", "AFN", "Afghan Afghani", R.drawable.afn),
    AED("د.إ", "AED", "UAE Dirham", R.drawable.ae);


    companion object {
        fun fromCode(code: String): Currency? {
            return values().find { it.code == code }
        }
    }
}