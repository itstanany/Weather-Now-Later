package com.itstanany.features.forecast.presentation.screen

sealed class ForecastIntent {
  data object ScreenOpened : ForecastIntent()
}
