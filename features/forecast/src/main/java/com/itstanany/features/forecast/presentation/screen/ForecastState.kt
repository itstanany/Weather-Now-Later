package com.itstanany.features.forecast.presentation.screen

import com.itstanany.domain.city.models.City
import com.itstanany.domain.weather.models.DailyWeather
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the Forecast screen.
 *
 * This data class holds all the necessary state properties required to render the Forecast screen,
 * including the list of weather forecasts, loading states, error messages, and the last searched city.
 *
 * @property forecasts The list of daily weather forecasts. Defaults to an empty immutable list.
 * @property isLoading Indicates whether forecast data is being fetched. Defaults to `false`.
 * @property error The error message to display if fetching forecast data fails. Defaults to `null`.
 * @property city The last searched city for which the forecast is displayed. Defaults to `null`.
 */
data class ForecastState(
  val forecasts: ImmutableList<DailyWeather> = persistentListOf(),
  val isLoading: Boolean = false,
  val error: String? = null,
  val city: City? = null
)
