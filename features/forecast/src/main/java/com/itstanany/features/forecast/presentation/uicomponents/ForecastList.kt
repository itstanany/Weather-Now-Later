package com.itstanany.features.forecast.presentation.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itstanany.domain.weather.models.DailyWeather
import kotlinx.collections.immutable.ImmutableList


@Composable
fun ForecastList(
  forecasts: ImmutableList<DailyWeather>,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(
      count = forecasts.size,
      key = { "${forecasts[it].maxTemp}-${forecasts[it].minTemp}" },
    ) { index ->
      ForecastItem(forecast = forecasts[index])
    }
  }
}
