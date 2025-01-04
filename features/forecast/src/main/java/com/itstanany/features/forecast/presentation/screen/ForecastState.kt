package com.itstanany.features.forecast.presentation.screen

import com.itstanany.domain.city.models.City
import com.itstanany.domain.weather.models.DailyWeather
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ForecastState(
  val forecasts: ImmutableList<DailyWeather> = persistentListOf(),
  val isLoading: Boolean = false,
  val error: String? = null,
  val city: City? = null
)
