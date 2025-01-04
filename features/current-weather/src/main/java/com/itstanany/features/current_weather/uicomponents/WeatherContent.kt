package com.itstanany.features.current_weather.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.itstanany.domain.weather.models.WeatherCondition
import com.itstanany.features.current_weather.R

@Composable
fun WeatherContent(
  condition: WeatherCondition?,
  conditionIconRes: Any?,
  maxTemp: Double,
  maxTempUnit: String,
  minTemp: Double,
  minTempUnit: String,
  feelsLikeTemp: Double?,
  feelsLikeTempUnit: String?,
  windSpeed: Double?,
  windSpeedUnit: String?,
  onForecastClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier.fillMaxWidth()
  ) {
    condition?.let {
      AsyncImage(
        model = conditionIconRes,
        contentDescription = it.label,
        modifier = Modifier.size(100.dp)
      )
      Text(
        text = it.label,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    TemperatureSection(
      maxTemp = maxTemp,
      maxTempUnit = maxTempUnit,
      minTemp = minTemp,
      minTempUnit = minTempUnit
    )

    Spacer(modifier = Modifier.height(24.dp))

    WeatherDetailsSection(
      feelsLikeTemp = feelsLikeTemp,
      windSpeed = windSpeed,
      feelsLikeTempUnit = feelsLikeTempUnit,
      windSpeedUnit = windSpeedUnit,
    )

    Spacer(modifier = Modifier.height(32.dp))

    Button(
      onClick = onForecastClick,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(stringResource(R.string.view_7_day_forecast))
    }
  }
}
