package com.itstanany.features.current_weather.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itstanany.domain.weather.models.WeatherCondition
import com.itstanany.features.current_weather.R
import com.itstanany.features.current_weather.uicomponents.CurrentWeatherTopBar
import com.itstanany.features.current_weather.uicomponents.WeatherContent

/**
 * The container composable for the Current Weather screen.
 *
 * This composable manages the UI and state for displaying the current weather data.
 * It interacts with the [CurrentWeatherViewModel] to fetch and display weather data
 * for the last searched city. It also provides navigation actions to the Forecast
 * and City Input screens.
 *
 * @param viewModel The [CurrentWeatherViewModel] that manages the state and logic for this screen.
 * @param onNavigateToForecast A lambda function to navigate to the Forecast screen.
 * @param onNavigateToSearch A lambda function to navigate to the City Input screen.
 * @param modifier A [Modifier] to apply to the root layout of this composable.
 */
@Composable
fun CurrentWeatherScreenContainer(
  viewModel: CurrentWeatherViewModel,
  onNavigateToForecast: () -> Unit,
  onNavigateToSearch: () -> Unit,
  modifier: Modifier = Modifier
) {
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) {
    viewModel.handleScreenOpened()
  }

  val conditionIconRes = remember(viewState.condition) {
    when (viewState.condition) {
      is WeatherCondition.Cloudy -> R.drawable.cloud
      is WeatherCondition.Rainy -> R.drawable.rain
      is WeatherCondition.Sunny -> R.drawable.sunny
      null -> null
    }
  }

  Surface(
    color = MaterialTheme.colorScheme.background,
    modifier = modifier.fillMaxSize()
  ) {
    Column(
      modifier = Modifier.fillMaxSize()
    ) {
      CurrentWeatherTopBar(
        city = viewState.city,
        onSearchClick = onNavigateToSearch
      )

      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
      ) {
        when {
          viewState.isLoading -> {
            CircularProgressIndicator(
              color = MaterialTheme.colorScheme.primary,
              modifier = Modifier.align(Alignment.Center)
            )
          }

          viewState.error != null -> {
            Text(
              text = viewState.error!!,
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.error,
              modifier = Modifier.align(Alignment.Center)
            )
          }

          else -> {
            WeatherContent(
              condition = viewState.condition,
              conditionIconRes = conditionIconRes,
              maxTemp = viewState.maxTemp,
              maxTempUnit = viewState.maxTempUnit,
              minTemp = viewState.minTemp,
              minTempUnit = viewState.minTempUnit,
              feelsLikeTemp = viewState.maxApparentTemp,
              feelsLikeTempUnit = viewState.maxApparentTempUnit,
              windSpeed = viewState.maxWindSpeed,
              windSpeedUnit = viewState.maxWindSpeedUnit,
              onForecastClick = onNavigateToForecast
            )
          }
        }
      }
    }
  }
}
