package com.itstanany.features.splash.presentation

/**
 * Represents the various states of the splash screen.
 */
sealed class SplashViewState {
  /**
   * State representing the loading state of the splash screen.
   */
  data object Loading : SplashViewState()

  /**
   * State representing navigation to the current weather screen.
   */
  data object NavigateToCurrentWeather : SplashViewState()

  /**
   * State representing navigation to the city input screen.
   */
  data object NavigateToCityInput : SplashViewState()

  /**
   * State representing navigation to the no internet screen.
   */
  data object NavigateToNoInternet : SplashViewState()
}