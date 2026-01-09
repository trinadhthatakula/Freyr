package com.valhalla.freyr.di

import com.valhalla.freyr.domain.usecase.GetWeatherUseCase
import com.valhalla.freyr.domain.usecase.RefreshWeatherUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetWeatherUseCase(get()) }
    factory { RefreshWeatherUseCase(get()) }
}
