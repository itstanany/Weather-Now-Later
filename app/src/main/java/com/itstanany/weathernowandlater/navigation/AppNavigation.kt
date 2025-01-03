package com.itstanany.weathernowandlater.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.itstanany.core.navigation.NavRoutes
import com.itstanany.features.city_input.CityInputScreenContainer
import com.itstanany.features.current_weather.CurrentWeatherScreenContainer
import com.itstanany.features.splash.presentation.SplashScreen
import com.itstanany.no_internet.NoInternetScreenContainer

@Composable
fun AppNavigation() {
  val navController = rememberNavController()

  val onNavToCityInput: () -> Unit = remember(navController) {
    {
      navController.navigate(NavRoutes.CityInput) {
        popUpTo(NavRoutes.Splash) { inclusive = true }
      }
    }
  }

  val onNavToCurrentWeather: () -> Unit = remember(navController) {
    {
      navController.navigate(NavRoutes.CurrentWeather) {
        popUpTo(NavRoutes.Splash) { inclusive = true }
      }
    }
  }

  val onNavToNoInternet: () -> Unit = remember(navController) {
    {
      navController.navigate(NavRoutes.NoInternet) {
        popUpTo(NavRoutes.Splash) { inclusive = true }
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
        onNavigateToCityInput = onNavToCityInput,
        onNavigateToCurrentWeather = onNavToCurrentWeather,
        onNavigateToNoInternet = onNavToNoInternet
      )
    }

    composable<NavRoutes.CityInput> {
      CityInputScreenContainer(
      )
    }

    composable<NavRoutes.CurrentWeather> {
      CurrentWeatherScreenContainer()
    }

    composable<NavRoutes.NoInternet> {
      NoInternetScreenContainer(
      )
    }
  }
}

