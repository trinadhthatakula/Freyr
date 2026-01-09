package com.valhalla.freyr.di

import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.valhalla.freyr.data.local.WeatherDatabase
import com.valhalla.freyr.data.location.DefaultLocationTracker
import com.valhalla.freyr.data.remote.WeatherApi
import com.valhalla.freyr.data.repository.WeatherRepositoryImpl
import com.valhalla.freyr.domain.location.LocationTracker
import com.valhalla.freyr.domain.repository.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }

    single { WeatherApi(get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            "weather_db"
        ).build()
    }

    single { get<WeatherDatabase>().weatherDao }

    single { LocationServices.getFusedLocationProviderClient(androidContext()) }

    single<LocationTracker> {
        DefaultLocationTracker(
            locationClient = get(),
            application = androidApplication()
        )
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(get(), get())
    }
}
