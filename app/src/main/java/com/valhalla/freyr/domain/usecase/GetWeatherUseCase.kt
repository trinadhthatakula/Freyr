package com.valhalla.freyr.domain.usecase

import com.valhalla.freyr.domain.model.Weather
import com.valhalla.freyr.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {
    operator fun invoke(lat: Double, lon: Double): Flow<Weather?> {
        return repository.getWeatherData(lat, lon)
    }
}
