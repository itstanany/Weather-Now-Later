package com.itstanany.features.current_weather

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.usecases.GetLastSearchedCityUseCase
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.models.WeatherCondition
import com.itstanany.domain.weather.usecases.GetCurrentWeatherUseCase
import com.itstanany.features.current_weather.screen.CurrentWeatherScreenContainer
import com.itstanany.features.current_weather.screen.CurrentWeatherViewModel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import com.itstanany.domain.common.Result
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CurrentWeatherScreenTest {
  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @get:Rule(order = 1)
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @MockK
  private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
  @MockK
  private lateinit var getLastSearchedCityUseCase: GetLastSearchedCityUseCase

  private lateinit var viewModel: CurrentWeatherViewModel
  private val testCity = City("London", "UK", 51.5074f, -0.1278f, 1)

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    viewModel = CurrentWeatherViewModel(getCurrentWeatherUseCase, getLastSearchedCityUseCase)
  }

  @Test
  fun showsLoadingState() {
    // Given
    coEvery { getLastSearchedCityUseCase() } returns flowOf(testCity)

    // When
    composeTestRule.setContent {
      CurrentWeatherScreenContainer(
        viewModel = viewModel,
        onNavigateToForecast = {},
        onNavigateToSearch = {}
      )
    }

    // Then
    composeTestRule
      .onNodeWithTag("loadingIndicator")
      .assertIsDisplayed()
  }

  @Test
  fun showsWeatherDataWhenLoadingSucceeds() {
    // Given
    val weather = createMockWeather()
    coEvery { getLastSearchedCityUseCase() } returns flowOf(testCity)
    coEvery { getCurrentWeatherUseCase(testCity) } returns Result.Success(weather)

    // When
    composeTestRule.setContent {
      CurrentWeatherScreenContainer(
        viewModel = viewModel,
        onNavigateToForecast = {},
        onNavigateToSearch = {}
      )
    }

    // Then
    composeTestRule.onNodeWithTag("weatherContent").assertIsDisplayed()
    composeTestRule.onNodeWithText("20.0 °C").assertIsDisplayed()
    composeTestRule.onNodeWithText("15.0 °C").assertIsDisplayed()
  }

  @Test
  fun showsErrorWhenLoadingFails() {
    // Given
    coEvery { getLastSearchedCityUseCase() } returns flowOf(null)

    // When
    composeTestRule.setContent {
      CurrentWeatherScreenContainer(
        viewModel = viewModel,
        onNavigateToForecast = {},
        onNavigateToSearch = {}
      )
    }

    // Then
    composeTestRule.onNodeWithText("No last searched city").assertIsDisplayed()
  }

  @Test
  fun navigatesToForecastWhenForecastButtonClicked() {
    // Given
    var navigated = false
    val weather = createMockWeather()
    coEvery { getLastSearchedCityUseCase() } returns flowOf(testCity)
    coEvery { getCurrentWeatherUseCase(testCity) } returns com.itstanany.domain.common.Result.Success(weather)

    // When
    composeTestRule.setContent {
      CurrentWeatherScreenContainer(
        viewModel = viewModel,
        onNavigateToForecast = { navigated = true },
        onNavigateToSearch = {}
      )
    }

    // Then
    composeTestRule.onNodeWithTag("forecastButton").performClick()
    assertTrue(navigated)
  }

  private fun createMockWeather() = DailyWeather(
    maxTemp = 20.0,
    minTemp = 15.0,
    condition = WeatherCondition.Sunny("Clear sky"),
    maxTempUnit = "°C",
    minTempUnit = "°C",
    maxApparentTemp = 22.0,
    minApparentTemp = 13.0,
    maxWindSpeed = 10.0,
    maxWindSpeedUnit = "km/h",
    date = LocalDate.now(), maxApparentTempUnit = null, minApparentTempUnit = null,

  )
}

