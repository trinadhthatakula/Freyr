package com.valhalla.freyr.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valhalla.freyr.domain.location.LocationTracker
import com.valhalla.freyr.domain.usecase.GetWeatherUseCase
import com.valhalla.freyr.domain.usecase.RefreshWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val refreshWeatherUseCase: RefreshWeatherUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    fun loadWeatherInfo() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                // Fetch the weather data from DB (Flow)
                launch {
                    getWeatherUseCase(location.latitude, location.longitude).collectLatest { weather ->
                        _state.update { it.copy(weather = weather, isLoading = false) }
                    }
                }
                // Trigger a network refresh
                refreshWeatherUseCase(location.latitude, location.longitude)
            } else {
                _state.update { 
                    it.copy(
                        isLoading = false, 
                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                    ) 
                }
            }
        }
    }
}
