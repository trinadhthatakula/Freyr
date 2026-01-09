package com.valhalla.freyr.data.remote

import com.valhalla.freyr.data.remote.dto.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherApi(private val client: HttpClient) {
    suspend fun getWeatherData(lat: Double, lon: Double): WeatherResponse {
        return client.get("https://api.open-meteo.com/v1/forecast") {
            parameter("latitude", lat)
            parameter("longitude", lon)
            parameter("current_weather", true)
            parameter("daily", "weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset")
            parameter("hourly", "temperature_2m,weathercode,relativehumidity_2m")
            parameter("timezone", "auto")
        }.body()
    }
}
