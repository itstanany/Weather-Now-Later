package com.itstanany.weathernowandlater.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.itstanany.core.navigation.NavRoutes
import com.itstanany.features.city_input.presentation.screen.CityInputScreenContainer
import com.itstanany.features.current_weather.presentation.screen.CurrentWeatherScreenContainer
import com.itstanany.features.forecast.presentation.screen.ForecastScreenContainer
import com.itstanany.features.splash.presentation.screen.SplashScreen
import com.itstanany.no_internet.NoInternetScreenContainer

/**
 * The main navigation composable for the application.
 *
 * This function sets up the navigation graph using Jetpack Navigation and defines the navigation
 * logic between different screens in the app. It uses a [NavController] to manage navigation
 * and provides lambda functions for navigating between screens.
 *
 * The navigation graph includes the following destinations:
 * - [NavRoutes.Splash]: The splash screen, which is the start destination.
 * - [NavRoutes.CityInput]: The screen for entering a city name.
 * - [NavRoutes.CurrentWeather]: The screen displaying the current weather for a selected city.
 * - [NavRoutes.NoInternet]: The screen shown when there is no internet connection.
 * - [NavRoutes.Forecast]: The screen displaying the weather forecast for a selected city.
 *
 * Each destination is associated with a composable screen, and navigation actions are passed
 * to the respective screens as lambda functions.
 */
@Composable
fun AppNavigation() {
  val navController = rememberNavController()

  val onNavToCityInputSingleTop: () -> Unit = remember(navController) {
    {
      navController.navigate(NavRoutes.CityInput) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
      }
    }
  }

  val onNavToCurrentWeatherSingleTop: () -> Unit = remember(navController) {
    {
      navController.navigate(NavRoutes.CurrentWeather) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
      }
    }
  }

  val onNavigateToCityInput = remember(navController) {
    {
      navController.navigate(NavRoutes.CityInput)
    }
  }

  val onNavToNoInternet: () -> Unit = remember(navController) {
    {
      navController.navigate(NavRoutes.NoInternet) {
        popUpTo(NavRoutes.Splash) { inclusive = true }
      }
    }
  }

  val onNavToForecast: () -> Unit = remember(navController) {
    {
      navController.navigate(NavRoutes.Forecast)
    }
  }

  val onNavToFirstScreen: () -> Unit = remember(navController) {
    {
      navController.navigate(NavRoutes.Splash) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
      }
    }
  }

  NavHost(
    navController = navController,
    startDestination = NavRoutes.Splash
  ) {
    composable<NavRoutes.Splash> {
      SplashScreen(
        viewModel = hiltViewModel(),
        onNavigateToCityInput = onNavToCityInputSingleTop,
        onNavigateToCurrentWeather = onNavToCurrentWeatherSingleTop,
        onNavigateToNoInternet = onNavToNoInternet
      )
    }

    composable<NavRoutes.CityInput> {
      CityInputScreenContainer(
        viewModel = hiltViewModel(),
        onNavigateToCurrentWeather = onNavToCurrentWeatherSingleTop
      )
    }

    composable<NavRoutes.CurrentWeather> {
      CurrentWeatherScreenContainer(
        viewModel = hiltViewModel(),
        onNavigateToForecast = onNavToForecast,
        onNavigateToSearch = onNavigateToCityInput,
      )
    }

    composable<NavRoutes.NoInternet> {
      NoInternetScreenContainer(
        onRetry = onNavToFirstScreen,
      )
    }

    composable<NavRoutes.Forecast> {
      ForecastScreenContainer(
        viewModel = hiltViewModel()
      )
    }
  }
}

