package com.itstanany.features.current_weather.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.itstanany.weathernowandlater.weather_utils.TemperatureUtils


@Composable
fun WeatherDetailsSection(
  feelsLikeTemp: Double?,
  windSpeed: Double?,
  feelsLikeTempUnit: String? = "",
  windSpeedUnit: String? = "",
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    feelsLikeTemp?.let { temp ->
      DetailItem(
        label = "Feels like",
        value = TemperatureUtils.formatTemperature(temp, feelsLikeTempUnit ?: "")
      )
    }
    windSpeed?.let { speed ->
      DetailItem(
        label = "Wind",
        value = TemperatureUtils.formatTemperature(speed, windSpeedUnit ?: "")
      )
    }
  }
}
