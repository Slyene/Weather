package com.devlab.weather.model

data class WeatherModel(
    val city: String = "",
    val date: String = "",
    val currentTemp: String = "",
    val condition: String = "",
    val icon: String = "",
    val maxTemp: String = "",
    val minTemp: String = "",
    val hour: String = "",
)
