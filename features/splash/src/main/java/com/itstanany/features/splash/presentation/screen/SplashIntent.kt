package com.itstanany.features.splash.presentation.screen

sealed class SplashIntent {
  data object CheckInitialState : SplashIntent()
  data object NavigationActionFinished : SplashIntent()
}
