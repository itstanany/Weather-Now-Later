package com.itstanany.weathernowandlater.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.itstanany.core.navigation.NavRoutes
import com.itstanany.features.city_input.presentation.screen.CityInputScreenContainer
import com.itstanany.features.current_weather.screen.CurrentWeatherScreenContainer
import com.itstanany.features.forecast.presentation.screen.ForecastScreenContainer
import com.itstanany.features.splash.presentation.screen.SplashScreen
import com.itstanany.no_internet.NoInternetScreenContainer

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

