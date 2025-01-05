package com.itstanany.features.forecast.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itstanany.domain.city.usecases.GetLastSearchedCityUseCase
import com.itstanany.domain.common.Result
import com.itstanany.domain.weather.usecases.GetForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Forecast screen.
 *
 * This ViewModel manages the state and logic for fetching and displaying the weather forecast
 * for the last searched city. It interacts with the [GetForecastUseCase] to fetch forecast data
 * and the [GetLastSearchedCityUseCase] to retrieve the last searched city.
 *
 * @property getForecastUseCase The use case for fetching weather forecast data for a city.
 * @property getLastSearchedCityUseCase The use case for retrieving the last searched city.
 */
@HiltViewModel
class ForecastViewModel @Inject constructor(
  private val getForecastUseCase: GetForecastUseCase,
  private val getLastSearchedCityUseCase: GetLastSearchedCityUseCase,
) : ViewModel() {

  private val _state = MutableStateFlow(ForecastState())
  val state: StateFlow<ForecastState> = _state.asStateFlow()

  private var screenOpened = false
  private var loadingForecastJob: Job? = null

  /**
   * Processes the given intent to trigger specific actions in the ViewModel.
   *
   * This method handles the [ForecastIntent.ScreenOpened] intent, which triggers
   * the loading of forecast data when the screen is opened for the first time.
   *
   * @param intent The intent to process.
   */
  fun processIntent(intent: ForecastIntent) {
    when (intent) {
      is ForecastIntent.ScreenOpened -> {
        if (!screenOpened) {
          screenOpened = true
          loadForecast()
        }
      }
    }
  }

  /**
   * Loads the weather forecast data for the last searched city.
   *
   * This method cancels any ongoing loading job and starts a new one to fetch the last searched city
   * and its forecast data.
   */
  private fun loadForecast() {
    loadingForecastJob?.cancel()
    loadingForecastJob = viewModelScope.launch {
      _state.update { it.copy(isLoading = true) }
      try {
        val cityFlow = getLastSearchedCityUseCase()
        cityFlow.collect { city ->
          if (city == null) {
            _state.update {
              it.copy(
                error = "No city found",
                isLoading = false,
              )
            }
          } else {
            _state.update { it.copy(city = city) }
            when (val forecasts = getForecastUseCase(city)) {
              is Result.Failure -> {
                _state.update {
                  it.copy(
                    error = forecasts.error.localizedMessage,
                    isLoading = false
                  )
                }
              }

              is Result.Success -> {
                _state.update {
                  it.copy(
                    forecasts = forecasts.data.toImmutableList(),
                    isLoading = false,
                    error = null,
                  )
                }
              }
            }
          }
        }

      } catch (e: Exception) {
        _state.update {
          it.copy(
            error = e.message,
            isLoading = false
          )
        }
      }
    }
  }
}

