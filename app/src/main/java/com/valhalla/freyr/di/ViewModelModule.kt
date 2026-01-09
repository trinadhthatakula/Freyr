package com.valhalla.freyr.di

import com.valhalla.freyr.presentation.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        WeatherViewModel(
            getWeatherUseCase = get(),
            refreshWeatherUseCase = get(),
            locationTracker = get()
        )
    }
}
