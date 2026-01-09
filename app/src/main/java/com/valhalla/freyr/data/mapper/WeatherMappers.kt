package com.valhalla.freyr.data.mapper

import com.valhalla.freyr.data.local.entity.DailyWeatherEntity
import com.valhalla.freyr.data.local.entity.WeatherEntity
import com.valhalla.freyr.data.remote.dto.WeatherResponse
import com.valhalla.freyr.domain.model.DailyWeather
import com.valhalla.freyr.domain.model.Weather

fun WeatherResponse.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        latitude = latitude,
        longitude = longitude,
        temperature = currentWeather?.temperature ?: 0.0,
        windSpeed = currentWeather?.windspeed ?: 0.0,
        weatherCode = currentWeather?.weatherCode ?: 0,
        humidity = hourly?.humidity?.firstOrNull(),
        time = currentWeather?.time ?: ""
    )
}

fun WeatherResponse.toDailyWeatherEntities(): List<DailyWeatherEntity> {
    val daily = daily ?: return emptyList()
    return daily.time.indices.map { i ->
        DailyWeatherEntity(
            date = daily.time[i],
            maxTemp = daily.tempMax[i],
            minTemp = daily.tempMin[i],
            weatherCode = daily.weatherCode[i],
            sunrise = daily.sunrise[i],
            sunset = daily.sunset[i]
        )
    }
}

fun WeatherEntity.toWeather(dailyForecast: List<DailyWeatherEntity>): Weather {
    return Weather(
        temperature = temperature,
        windSpeed = windSpeed,
        weatherCode = weatherCode,
        humidity = humidity,
        time = time,
        latitude = latitude,
        longitude = longitude,
        dailyForecast = dailyForecast.map { it.toDailyWeather() }
    )
}

fun DailyWeatherEntity.toDailyWeather(): DailyWeather {
    return DailyWeather(
        date = date,
        maxTemp = maxTemp,
        minTemp = minTemp,
        weatherCode = weatherCode,
        sunrise = sunrise,
        sunset = sunset
    )
}
