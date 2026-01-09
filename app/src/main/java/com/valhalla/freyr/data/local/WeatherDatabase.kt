package com.valhalla.freyr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.valhalla.freyr.data.local.dao.WeatherDao
import com.valhalla.freyr.data.local.entity.DailyWeatherEntity
import com.valhalla.freyr.data.local.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class, DailyWeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao
}
