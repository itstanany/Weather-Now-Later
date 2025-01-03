package com.itstanany.features.splash.presentation.components

import android.app.Activity
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import com.itstanany.features.splash.presentation.SplashViewState


/**
 * Handles splash screen navigation and system UI drawing following clean architecture and Compose best practices.
 * This composable manages the core splash screen behavior by controlling system drawing and navigation based on view state.
 *
 * Technical decisions aligned with interview requirements:
 * 1. Uses rememberUpdatedState to prevent memory churn during recomposition
 * 2. Implements proper state management following MVI pattern
 * 3. Handles navigation through lambda callbacks for better testability
 * 4. Uses DisposableEffect for proper lifecycle management
 * 5. Follows clean code principles with single responsibility
 *
 * @param viewState Current state of the splash screen following MVI pattern
 * @param onNavigateToCityInput Callback for navigating to city input screen when no cached city exists
 * @param onNavigateToCurrentWeather Callback for navigating to weather screen when cached city exists
 * @param onNavigateToNoInternet Callback for navigating to no internet screen on network failure
 * @param onNavigationActionFinished Callback to notify navigation completion
 */
@Composable
fun HandleSplashScreenNavigation(
  viewState: SplashViewState,
  onNavigateToCityInput: () -> Unit,
  onNavigateToCurrentWeather: () -> Unit,
  onNavigateToNoInternet: () -> Unit,
  onNavigationActionFinished: () -> Unit
) {
  val context = LocalContext.current
  if (context !is Activity) return

  val rootView = remember {
    context.findViewById<ViewGroup>(android.R.id.content)
  }

  // We used remembered value and avoid adding those params to remember key params
  // to avoid MEMORY CHURN
  val updateViewState = rememberUpdatedState(viewState)
  val latestOnNavigateToCityInput = rememberUpdatedState(onNavigateToCityInput)
  val latestOnNavigateToCurrentWeather = rememberUpdatedState(onNavigateToCurrentWeather)
  val latestOnNavigateToNoInternet = rememberUpdatedState(onNavigateToNoInternet)
  val latestOnNavigationActionFinished = rememberUpdatedState(onNavigationActionFinished)

  val listener = remember {
    object : ViewTreeObserver.OnPreDrawListener {
      override fun onPreDraw(): Boolean = when (updateViewState.value) {
        SplashViewState.Loading -> false
        SplashViewState.NavigateToCityInput -> {
          handleNavigation(
            onNavigate = latestOnNavigateToCityInput.value,
            onFinish = latestOnNavigationActionFinished.value,
            removeListener = {
              rootView.viewTreeObserver.removeOnPreDrawListener(this)
            }
          )
          true
        }

        SplashViewState.NavigateToCurrentWeather -> {
          handleNavigation(
            onNavigate = latestOnNavigateToCurrentWeather.value,
            onFinish = latestOnNavigationActionFinished.value,
            removeListener = {
              rootView.viewTreeObserver.removeOnPreDrawListener(this)
            }
          )
          true
        }

        SplashViewState.NavigateToNoInternet -> {
          handleNavigation(
            onNavigate = latestOnNavigateToNoInternet.value,
            onFinish = latestOnNavigationActionFinished.value,
            removeListener = {
              rootView.viewTreeObserver.removeOnPreDrawListener(this)
            }
          )
          true
        }
      }
    }
  }

  DisposableEffect(rootView) {
    if (viewState is SplashViewState.Loading) {
      rootView.viewTreeObserver.addOnPreDrawListener(listener)
    }
    onDispose {
      rootView.viewTreeObserver.removeOnPreDrawListener(listener)
    }
  }
}

/**
 * Utility function to handle navigation flow in a consistent manner.
 * Executes navigation callbacks in the correct order while maintaining clean code principles.
 *
 * @param onNavigate Callback to execute the actual navigation
 * @param onFinish Callback to notify navigation completion
 * @param removeListener Callback to clean up the PreDrawListener
 */
private fun handleNavigation(
  onNavigate: () -> Unit,
  onFinish: () -> Unit,
  removeListener: () -> Unit
) {
  onNavigate()
  onFinish()
  removeListener()
}