package com.itstanany.features.forecast.presentation.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itstanany.domain.weather.models.DailyWeather
import java.time.format.DateTimeFormatter

@Composable
fun ForecastItem(
  forecast: DailyWeather,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
  ) {
    Row(
      modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = forecast.date.format(DateTimeFormatter.ofPattern("EEEE, MMM d")),
          style = MaterialTheme.typography.titleMedium
        )
        Text(
          text = forecast.condition.label,
          style = MaterialTheme.typography.bodyMedium
        )
      }

      Column(horizontalAlignment = Alignment.End) {
        Text(
          text = "${forecast.maxTemp}°",
          style = MaterialTheme.typography.titleMedium
        )
        Text(
          text = "${forecast.minTemp}°",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}
