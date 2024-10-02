package com.example.atry.navigate

sealed class Navigate_data(val route: String) {
    object Home:Navigate_data("home")
    object ShowView:Navigate_data("result_bmi/{bmiValue}")
}