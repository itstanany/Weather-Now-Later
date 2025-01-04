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

@HiltViewModel
class ForecastViewModel @Inject constructor(
  private val getForecastUseCase: GetForecastUseCase,
  private val getLastSearchedCityUseCase: GetLastSearchedCityUseCase,
) : ViewModel() {

  private val _state = MutableStateFlow(ForecastState())
  val state: StateFlow<ForecastState> = _state.asStateFlow()

  private var screenOpened = false
  private var loadingForecastJob: Job? = null

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

