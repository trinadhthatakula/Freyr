package com.valhalla.freyr.presentation

import com.valhalla.freyr.domain.model.Weather

data class WeatherState(
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
