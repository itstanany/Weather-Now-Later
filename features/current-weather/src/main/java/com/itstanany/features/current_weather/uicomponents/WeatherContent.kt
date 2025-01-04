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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.itstanany.features.current_weather.R
import com.itstanany.features.current_weather.screen.CurrentWeatherState

@Composable
fun WeatherContent(
  viewState: CurrentWeatherState,
  onForecastClick: () -> Unit,
  conditionIconRes: Any?,
  modifier: Modifier = Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier.fillMaxWidth()
  ) {
    viewState.condition?.let { condition ->
      AsyncImage(
        model = conditionIconRes,
        contentDescription = condition.label,
        modifier = Modifier.size(100.dp)
      )
      Text(
        text = condition.label,
        style = MaterialTheme.typography.headlineMedium
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    TemperatureSection(
      maxTemp = viewState.maxTemp,
      maxTempUnit = viewState.maxTempUnit,
      minTemp = viewState.minTemp,
      minTempUnit = viewState.minTempUnit
    )

    Spacer(modifier = Modifier.height(24.dp))

    WeatherDetailsSection(
      feelsLikeTemp = viewState.maxApparentTemp,
      windSpeed = viewState.maxWindSpeed,
      feelsLikeTempUnit = viewState.maxApparentTempUnit,
      windSpeedUnit = viewState.maxWindSpeedUnit,
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
