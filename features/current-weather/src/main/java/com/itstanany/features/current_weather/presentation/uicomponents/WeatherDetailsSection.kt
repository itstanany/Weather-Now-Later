package com.itstanany.features.current_weather.presentation.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.itstanany.weathernowandlater.weather_utils.TemperatureUtils


@Composable
fun WeatherDetailsSection(
  feelsLikeTemp: Double?,
  windSpeed: Double?,
  modifier: Modifier = Modifier,
  feelsLikeTempUnit: String? = "",
  windSpeedUnit: String? = ""
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
