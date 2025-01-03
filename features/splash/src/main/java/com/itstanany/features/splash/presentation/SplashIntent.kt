package com.itstanany.features.splash.presentation

sealed class SplashIntent {
  data object CheckInitialState : SplashIntent()
  data object NavigationActionFinished : SplashIntent()
}
