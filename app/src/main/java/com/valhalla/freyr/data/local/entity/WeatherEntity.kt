package com.valhalla.freyr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val id: Int = 0, // We only store the latest weather for now
    val latitude: Double,
    val longitude: Double,
    val temperature: Double,
    val windSpeed: Double,
    val weatherCode: Int,
    val humidity: Int?,
    val time: String,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Entity(tableName = "daily_weather")
data class DailyWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val weatherCode: Int,
    val sunrise: String,
    val sunset: String
)
