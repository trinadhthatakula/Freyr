package com.valhalla.freyr.domain.repository

import com.valhalla.freyr.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherData(lat: Double, lon: Double): Flow<Weather?>
    suspend fun refreshWeather(lat: Double, lon: Double)
}
