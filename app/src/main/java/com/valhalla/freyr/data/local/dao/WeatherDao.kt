package com.valhalla.freyr.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.valhalla.freyr.data.local.entity.DailyWeatherEntity
import com.valhalla.freyr.data.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE id = 0")
    fun getLatestWeather(): Flow<WeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM daily_weather")
    fun getDailyForecast(): Flow<List<DailyWeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyForecast(dailyForecast: List<DailyWeatherEntity>)

    @Query("DELETE FROM daily_weather")
    suspend fun clearDailyForecast()

    @Transaction
    suspend fun updateWeatherAndForecast(weather: WeatherEntity, dailyForecast: List<DailyWeatherEntity>) {
        insertWeather(weather)
        clearDailyForecast()
        insertDailyForecast(dailyForecast)
    }

}
