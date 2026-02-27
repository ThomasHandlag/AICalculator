package com.example.calculator.navigation


sealed class Route(val path: String) {
    object Splash: Route("splash")
    object Onboard: Route("onboard")
    object Home: Route("home")
    object BasicCal: Route("basic")
    object History: Route("history")
    object AiCal: Route("ai")
    object AiHistory: Route("ai_history")
    object UnitCon: Route("unit")
    object CurCon: Route("currency")
    object DiscountCal: Route("discount")
    object TipCal: Route("tip")
    object DateCal: Route("date")
    object LoanCal: Route("loan")
    object GPACal: Route("gpa")
    object BMICal: Route("bmi")
    object Setting: Route("setting")
}