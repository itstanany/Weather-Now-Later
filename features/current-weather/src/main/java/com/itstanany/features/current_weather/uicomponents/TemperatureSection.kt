package com.itstanany.features.current_weather.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TemperatureSection(
  maxTemp: Double,
  maxTempUnit: String,
  minTemp: Double,
  minTempUnit: String,
  modifier: Modifier = Modifier
) {
  Row(
    horizontalArrangement = Arrangement.SpaceEvenly,
    modifier = modifier.fillMaxWidth()
  ) {
    Text(
      text = "H: $maxTemp$maxTempUnit",
      style = MaterialTheme.typography.displaySmall
    )
    Text(
      text = "L: $minTemp$minTempUnit",
      style = MaterialTheme.typography.displaySmall
    )
  }
}
