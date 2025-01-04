package com.itstanany.weathernowandlater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.itstanany.weathernowandlater.navigation.AppNavigation
import com.itstanany.weathernowandlater.ui.theme.WeatherNowAndLaterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      WeatherNowAndLaterTheme {
        AppNavigation()
      }
    }
  }
}
