package com.valhalla.freyr.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.valhalla.freyr.domain.model.DailyWeather
import com.valhalla.freyr.domain.model.Weather
import com.valhalla.freyr.ui.theme.NorseGold
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WeatherDashboardScreen(
    viewModel: WeatherViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(locationPermissionState.status.isGranted) {
        if (locationPermissionState.status.isGranted) {
            viewModel.loadWeatherInfo()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("FREYR", fontWeight = FontWeight.Bold, letterSpacing = 4.sp) },
                actions = {
                    IconButton(onClick = { viewModel.loadWeatherInfo() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = NorseGold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = Color.Unspecified,
                    navigationIconContentColor = Color.Unspecified,
                    titleContentColor = NorseGold,
                    actionIconContentColor = Color.Unspecified
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (locationPermissionState.status.isGranted) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    state.weather?.let { weather ->
                        CurrentWeatherCard(weather)
                        Spacer(modifier = Modifier.height(24.dp))
                        SolarDataCard(weather)
                        Spacer(modifier = Modifier.height(24.dp))
                        ForecastSection(weather.dailyForecast)
                    }

                    if (state.isLoading) {
                        CircularProgressIndicator(color = NorseGold, modifier = Modifier.padding(16.dp))
                    }

                    state.error?.let { error ->
                        Text(text = error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
                    }
                }
            } else {
                PermissionRequestContent {
                    locationPermissionState.launchPermissionRequest()
                }
            }
        }
    }
}

@Composable
fun CurrentWeatherCard(weather: Weather) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(24.dp).align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Current Weather", style = MaterialTheme.typography.labelLarge, color = NorseGold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${weather.temperature}°C",
                style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.Air, contentDescription = null, tint = NorseGold, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${weather.windSpeed} km/h", color = Color.LightGray)
                Spacer(modifier = Modifier.width(24.dp))
                Icon(Icons.Rounded.WaterDrop, contentDescription = null, tint = NorseGold, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${weather.humidity}%", color = Color.LightGray)
            }
        }
    }
}

@Composable
fun SolarDataCard(weather: Weather) {
    val todayForecast = weather.dailyForecast.firstOrNull() ?: return
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SolarItem(label = "Sunrise", time = todayForecast.sunrise.split("T").last(), icon = Icons.Rounded.WbSunny)
            SolarItem(label = "Sunset", time = todayForecast.sunset.split("T").last(), icon = Icons.Rounded.WbTwilight)
        }
    }
}

@Composable
fun SolarItem(label: String, time: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = NorseGold, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = Color.LightGray)
        Text(text = time, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun ForecastSection(forecasts: List<DailyWeather>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Daily Forecast", style = MaterialTheme.typography.titleLarge, color = NorseGold, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(forecasts.take(7)) { forecast ->
                ForecastItem(forecast)
            }
        }
    }
}

@Composable
fun ForecastItem(forecast: DailyWeather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = forecast.date, color = Color.White, fontWeight = FontWeight.Medium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "${forecast.maxTemp}°", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${forecast.minTemp}°", color = Color.Gray)
        }
    }
}

@Composable
fun PermissionRequestContent(onRequestPermission: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Rounded.LocationOn, contentDescription = null, tint = NorseGold, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Grant location access to see the weather of your realm.", color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRequestPermission,
            colors = ButtonDefaults.buttonColors(containerColor = NorseGold, contentColor = Color.Black)
        ) {
            Text("Grant Access")
        }
    }
}
