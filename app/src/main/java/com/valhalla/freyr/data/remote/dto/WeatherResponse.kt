package com.valhalla.freyr.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("current_weather") val currentWeather: CurrentWeatherDto? = null,
    @SerialName("daily") val daily: DailyDto? = null,
    @SerialName("hourly") val hourly: HourlyDto? = null,
    @SerialName("timezone") val timezone: String
)

@Serializable
data class CurrentWeatherDto(
    @SerialName("temperature") val temperature: Double,
    @SerialName("windspeed") val windspeed: Double,
    @SerialName("winddirection") val winddirection: Double,
    @SerialName("weathercode") val weatherCode: Int,
    @SerialName("time") val time: String
)

@Serializable
data class DailyDto(
    @SerialName("time") val time: List<String>,
    @SerialName("weathercode") val weatherCode: List<Int>,
    @SerialName("temperature_2m_max") val tempMax: List<Double>,
    @SerialName("temperature_2m_min") val tempMin: List<Double>,
    @SerialName("sunrise") val sunrise: List<String>,
    @SerialName("sunset") val sunset: List<String>
)

@Serializable
data class HourlyDto(
    @SerialName("time") val time: List<String>,
    @SerialName("temperature_2m") val temperature: List<Double>,
    @SerialName("weathercode") val weatherCode: List<Int>,
    @SerialName("relativehumidity_2m") val humidity: List<Int>
)
