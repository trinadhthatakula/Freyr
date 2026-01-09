package com.valhalla.freyr.domain.model

data class Weather(
    val temperature: Double,
    val windSpeed: Double,
    val weatherCode: Int,
    val humidity: Int?,
    val time: String,
    val latitude: Double,
    val longitude: Double,
    val dailyForecast: List<DailyWeather> = emptyList()
)

data class DailyWeather(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val weatherCode: Int,
    val sunrise: String,
    val sunset: String
)
