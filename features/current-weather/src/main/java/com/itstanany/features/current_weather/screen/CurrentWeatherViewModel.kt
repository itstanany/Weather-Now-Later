package com.itstanany.features.current_weather.screen

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


@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
  private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
  private val getLastSearchedCityUseCase: GetLastSearchedCityUseCase
) : ViewModel() {

  private val _viewState = MutableStateFlow(CurrentWeatherState())
  val viewState: StateFlow<CurrentWeatherState> = _viewState.asStateFlow()

  private var screenOpened = false
  private var loadingWeatherJob: Job? = null

  fun handleScreenOpened() {
    if (screenOpened) return
    screenOpened = true
    loadLastSearchedCityWeather()
  }

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

  private suspend fun fetchWeatherForCity(city: City) {
    when (val result = getCurrentWeatherUseCase(city)) {
      is Result.Failure -> handleError(result.error)
      is Result.Success -> handleWeatherSuccess(result.data)
    }
  }

  private fun handleNoLastSearchedCity() {
    updateState {
      it.copy(
        error = "No last searched city",
        isLoading = false
      )
    }
  }

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

  private fun handleError(error: Throwable) {
    updateState {
      it.copy(
        error = error.localizedMessage ?: "An unexpected error occurred",
        isLoading = false
      )
    }
  }

  private fun updateCity(city: City) {
    updateState { it.copy(city = city) }
  }


  private fun updateState(transform: (CurrentWeatherState) -> CurrentWeatherState) {
    _viewState.update(transform)
  }
}

