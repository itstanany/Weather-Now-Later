package com.itstanany.weathernowandlater.navigation

import androidx.compose.runtime.Composable
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

  NavHost(
    navController = navController,
    startDestination = NavRoutes.Splash
  ) {
    composable<NavRoutes.Splash> {
      SplashScreen(
//        onNavigate = { route ->
//          navController.navigate(route) {
//            popUpTo(NavRoutes.Splash) { inclusive = true }
//          }
//        }
      )
    }

    composable<NavRoutes.CityInput> {
      CityInputScreenContainer(
//        onNavigateToWeather = {
//          navController.navigate(NavRoutes.CurrentWeather) {
//            popUpTo(NavRoutes.CityInput) { inclusive = true }
//          }
//        }
      )
    }

    composable<NavRoutes.CurrentWeather> {
      CurrentWeatherScreenContainer()
    }

    composable<NavRoutes.NoInternet> {
      NoInternetScreenContainer(
//        onRetry = {
//          navController.navigate(NavRoutes.Splash) {
//            popUpTo(0) { inclusive = true }
//          }
//        }
      )
    }
  }
}

