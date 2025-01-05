package com.itstanany.features.forecast.presentation.screen

/**
 * Represents the intents that can be processed by the [ForecastViewModel].
 *
 * This sealed class defines the possible actions or events that the ViewModel can handle.
 * Each intent corresponds to a specific user interaction or system event.
 */
sealed class ForecastIntent {

  /**
   * Represents the intent triggered when the Forecast screen is opened.
   *
   * This intent is used to initiate the loading of forecast data when the screen is first displayed.
   */
  data object ScreenOpened : ForecastIntent()
}
