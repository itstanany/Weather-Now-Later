package com.itstanany.features.splash.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itstanany.features.splash.presentation.components.HandleSplashScreenNavigation


/**
 * Splash screen composable implementing MVI architecture pattern as specified in technical requirements.
 * This composable serves as the initial screen of the Weather Now & Later app, handling:
 * - Initial app state verification
 * - Network connectivity check
 * - Navigation to appropriate screens based on state
 *
 * Technical decisions aligned with interview requirements:
 * 1. Uses Jetpack Compose for UI as required
 * 2. Implements MVI pattern through state collection and intent processing
 * 3. Follows clean architecture by separating concerns
 * 4. Handles navigation through lambda callbacks for better testability
 * 5. Uses proper state management with collectAsStateWithLifecycle
 *
 * @param viewModel The splash screen's ViewModel following MVI pattern
 * @param onNavigateToCityInput Callback for navigating to city input screen when no cached city exists
 * @param onNavigateToCurrentWeather Callback for navigating to weather screen when cached city exists
 * @param onNavigateToNoInternet Callback for navigating to no internet screen on network failure
 * @param modifier Compose modifier for customizing the layout
 */
@Composable
fun SplashScreen(
  viewModel: SplashViewModel,
  onNavigateToCityInput: () -> Unit,
  onNavigateToCurrentWeather: () -> Unit,
  onNavigateToNoInternet: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  val onNavigationActionFinished: () -> Unit = remember {
    {
      viewModel.processIntent(SplashIntent.NavigationActionFinished)
    }
  }

  HandleSplashScreenNavigation(
    viewState = viewState,
    onNavigateToCityInput = onNavigateToCityInput,
    onNavigateToCurrentWeather = onNavigateToCurrentWeather,
    onNavigateToNoInternet = onNavigateToNoInternet,
    onNavigationActionFinished = onNavigationActionFinished,
  )

  LaunchedEffect(Unit) {
    viewModel.processIntent(SplashIntent.CheckInitialState)
  }
}