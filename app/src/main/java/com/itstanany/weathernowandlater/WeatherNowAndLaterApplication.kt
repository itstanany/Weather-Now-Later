package com.itstanany.weathernowandlater

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The main application class for the Weather Now and Later app.
 *
 * This class is annotated with [@HiltAndroidApp] to enable Hilt dependency injection
 * throughout the application. It initializes the Hilt components and sets up the
 * application context for dependency injection.
 *
 * This class extends [Application], making it the entry point for the app's initialization.
 */
@HiltAndroidApp
class WeatherNowAndLaterApplication: Application()
