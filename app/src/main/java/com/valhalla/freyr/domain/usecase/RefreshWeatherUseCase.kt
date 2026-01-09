package com.valhalla.freyr.domain.usecase

import com.valhalla.freyr.domain.repository.WeatherRepository

class RefreshWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double) {
        repository.refreshWeather(lat, lon)
    }
}
