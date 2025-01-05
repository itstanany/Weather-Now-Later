package com.itstanany.features.splash.presentation.screen

/**
 * Represents the intents that can be processed by the [SplashViewModel].
 *
 * This sealed class defines the possible actions or events that the ViewModel can handle.
 * Each intent corresponds to a specific user interaction or system event related to the Splash screen.
 */
sealed class SplashIntent {

  /**
   * Represents the intent triggered to check the initial state of the app.
   *
   * This intent is used to determine the appropriate navigation action when the Splash screen is first displayed,
   * such as checking if the user has a last searched city or if the app needs to fetch initial data.
   */
  data object CheckInitialState : SplashIntent()

  /**
   * Represents the intent triggered when a navigation action is completed.
   *
   * This intent is used to notify the ViewModel that a navigation action (e.g., navigating to the City Input screen
   * or the Current Weather screen) has finished, allowing the ViewModel to update its state accordingly.
   */
  data object NavigationActionFinished : SplashIntent()
}
