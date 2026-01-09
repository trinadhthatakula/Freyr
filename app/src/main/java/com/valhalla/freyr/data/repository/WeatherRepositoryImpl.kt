package com.valhalla.freyr.data.repository

import com.valhalla.freyr.data.local.dao.WeatherDao
import com.valhalla.freyr.data.mapper.toDailyWeatherEntities
import com.valhalla.freyr.data.mapper.toWeather
import com.valhalla.freyr.data.mapper.toWeatherEntity
import com.valhalla.freyr.data.remote.WeatherApi
import com.valhalla.freyr.domain.model.Weather
import com.valhalla.freyr.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    override fun getWeatherData(lat: Double, lon: Double): Flow<Weather?> {
        return combine(
            dao.getLatestWeather(),
            dao.getDailyForecast()
        ) { weatherEntity, dailyEntities ->
            weatherEntity?.toWeather(dailyEntities)
        }
    }

    override suspend fun refreshWeather(lat: Double, lon: Double) {
        try {
            val response = api.getWeatherData(lat, lon)
            val weatherEntity = response.toWeatherEntity()
            val dailyEntities = response.toDailyWeatherEntities()
            dao.updateWeatherAndForecast(weatherEntity, dailyEntities)
        } catch (e: Exception) {
            e.printStackTrace()
            // In a real app, we'd handle errors more gracefully
        }
    }
}
