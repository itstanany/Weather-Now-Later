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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.weathernowandlater.weather_utils.DateUtils
import com.itstanany.weathernowandlater.weather_utils.TemperatureUtils

@Composable
fun ForecastItem(
  forecast: DailyWeather,
  modifier: Modifier = Modifier
) {
  // todo: if compose stability emerges: stabilize it
  //   read: https://developer.android.com/develop/ui/compose/performance/stability/fix
  val date = remember(forecast.date) {
    DateUtils.formatToWeekDayAndMonth(forecast.date)
  }

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
          text = date,
          style = MaterialTheme.typography.titleMedium
        )
        Text(
          text = forecast.condition.label,
          style = MaterialTheme.typography.bodyMedium
        )
      }

      Column(horizontalAlignment = Alignment.End) {
        Text(
          text = TemperatureUtils.formatTemperature(forecast.maxTemp, forecast.maxTempUnit),
          style = MaterialTheme.typography.titleMedium
        )
        Text(
          text = TemperatureUtils.formatTemperature(forecast.minTemp, forecast.minTempUnit),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}
