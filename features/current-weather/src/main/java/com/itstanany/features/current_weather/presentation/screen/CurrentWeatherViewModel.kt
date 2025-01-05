package com.itstanany.features.current_weather.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.usecases.GetLastSearchedCityUseCase
import com.itstanany.domain.common.Result
import com.itstanany.domain.weather.models.DailyWeather
import com.itstanany.domain.weather.usecases.GetCurrentWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel for the Current Weather screen.
 *
 * This ViewModel manages the state and logic for fetching and displaying the current weather
 * for the last searched city. It interacts with the [GetCurrentWeatherUseCase] to fetch weather data
 * and the [GetLastSearchedCityUseCase] to retrieve the last searched city.
 *
 * @property getCurrentWeatherUseCase The use case for fetching current weather data for a city.
 * @property getLastSearchedCityUseCase The use case for retrieving the last searched city.
 */
@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
  private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
  private val getLastSearchedCityUseCase: GetLastSearchedCityUseCase
) : ViewModel() {

  /**
   * The internal mutable state flow for the ViewModel's state.
   */
  private val _viewState = MutableStateFlow(CurrentWeatherState())

  /**
   * The public immutable state flow representing the current state of the ViewModel.
   */
  val viewState: StateFlow<CurrentWeatherState> = _viewState.asStateFlow()

  /**
   * Tracks whether the screen has been opened to avoid redundant data fetching.
   */
  private var screenOpened = false
  /**
   * Job for managing the weather data loading operation.
   */
  private var loadingWeatherJob: Job? = null

  /**
   * Handles the screen being opened for the first time.
   *
   * This method ensures that weather data is only fetched once when the screen is opened.
   */
  fun handleScreenOpened() {
    if (screenOpened) return
    screenOpened = true
    loadLastSearchedCityWeather()
  }

  /**
   * Loads the weather data for the last searched city.
   *
   * This method cancels any ongoing loading job and starts a new one to fetch the last searched city
   * and its weather data.
   */
  private fun loadLastSearchedCityWeather() {
    loadingWeatherJob?.cancel()
    loadingWeatherJob = viewModelScope.launch {
      updateState { it.copy(isLoading = true) }
      try {
        fetchLastSearchedCity()
      } catch (e: Exception) {
        handleError(e)
      }
    }
  }

  /**
   * Fetches the last searched city from the data store.
   *
   * If a city is found, it updates the state with the city and fetches its weather data.
   * If no city is found, it handles the error accordingly.
   */
  private suspend fun fetchLastSearchedCity() {
    getLastSearchedCityUseCase().collect { city ->
      if (city == null) {
        handleNoLastSearchedCity()
      } else {
        updateCity(city)
        fetchWeatherForCity(city)
      }
    }
  }

  /**
   * Fetches the weather data for the given city.
   *
   * This method uses the [GetCurrentWeatherUseCase] to fetch weather data and updates the state
   * based on the result.
   *
   * @param city The city for which to fetch weather data.
   */
  private suspend fun fetchWeatherForCity(city: City) {
    when (val result = getCurrentWeatherUseCase(city)) {
      is Result.Failure -> handleError(result.error)
      is Result.Success -> handleWeatherSuccess(result.data)
    }
  }

  /**
   * Handles the case where no last searched city is found.
   *
   * This method updates the state to reflect the absence of a last searched city.
   */
  private fun handleNoLastSearchedCity() {
    updateState {
      it.copy(
        error = "No last searched city",
        isLoading = false
      )
    }
  }

  /**
   * Handles the successful fetching of weather data.
   *
   * This method updates the state with the fetched weather data.
   *
   * @param data The fetched weather data.
   */
  private fun handleWeatherSuccess(data: DailyWeather) {
    updateState {
      it.copy(
        minTemp = data.minTemp,
        minTempUnit = data.minTempUnit,
        maxTemp = data.maxTemp,
        maxTempUnit = data.maxTempUnit,
        minApparentTemp = data.minApparentTemp,
        minApparentTempUnit = data.minApparentTempUnit,
        maxApparentTemp = data.maxApparentTemp,
        maxApparentTempUnit = data.maxApparentTempUnit,
        maxWindSpeed = data.maxWindSpeed,
        maxWindSpeedUnit = data.maxWindSpeedUnit,
        condition = data.condition,
        isLoading = false,
      )
    }
  }

  /**
   * Handles errors that occur during data fetching.
   *
   * This method updates the state with the error message.
   *
   * @param error The error that occurred.
   */
  private fun handleError(error: Throwable) {
    updateState {
      it.copy(
        error = error.localizedMessage ?: "An unexpected error occurred",
        isLoading = false
      )
    }
  }

  /**
   * Updates the state with the given city.
   *
   * @param city The city to update in the state.
   */
  private fun updateCity(city: City) {
    updateState { it.copy(city = city) }
  }

  /**
   * Updates the ViewModel's state using the provided transformation function.
   *
   * @param transform The transformation function to apply to the current state.
   */
  private fun updateState(transform: (CurrentWeatherState) -> CurrentWeatherState) {
    _viewState.update(transform)
  }
}

