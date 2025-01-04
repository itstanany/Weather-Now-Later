package com.itstanany.features.splash.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itstanany.domain.city.usecases.GetLastSearchedCityUseCase
import com.itstanany.domain.common.Result
import com.itstanany.domain.network.usecases.CheckNetworkAvailabilityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel for the splash screen implementing MVI architecture pattern as specified in technical requirements.
 * This implementation showcases:
 * - Clean Architecture through use case implementation
 * - SOLID principles with single responsibility and dependency injection
 * - Proper state management using StateFlow
 * - Coroutine handling for asynchronous operations
 *
 * @property getLastSearchedCityUseCase Use case to retrieve the last searched city following clean architecture
 * @property checkNetworkAvailabilityUseCase Use case to check network connectivity following clean architecture
 *
 * Technical decisions aligned with interview requirements:
 * 1. Uses MVI pattern with intent processing and single view state
 * 2. Implements dependency injection using Hilt
 * 3. Follows clean architecture by depending on domain layer use cases
 * 4. Handles configuration changes properly
 * 5. Manages coroutine lifecycle using viewModelScope
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
  private val getLastSearchedCityUseCase: GetLastSearchedCityUseCase,
  private val checkNetworkAvailabilityUseCase: CheckNetworkAvailabilityUseCase,
) : ViewModel() {
  private val _viewState = MutableStateFlow<SplashViewState>(SplashViewState.Loading)
  val viewState: StateFlow<SplashViewState> = _viewState.asStateFlow()

  private var checkInitialStateJob: Job? = null

  /**
   * Processes UI intents following MVI pattern.
   * Handles two types of intents:
   * 1. CheckInitialState: Initiates the splash screen flow
   * 2. NavigationActionFinished: Cleanup after navigation
   *
   * @param intent The UI intent to process
   */
  fun processIntent(intent: SplashIntent) {
    when (intent) {
      SplashIntent.CheckInitialState -> {
        // to avoid redundant intent reduction on ui configuration change
        if (checkInitialStateJob == null) {
          checkInitialState()
        }
      }

      SplashIntent.NavigationActionFinished -> {
        checkInitialStateJob?.cancel()
        checkInitialStateJob = null
      }
    }
  }

  /**
   * Checks initial application state by:
   * 1. Verifying network connectivity
   * 2. Retrieving last searched city if network is available
   *
   * Implements error handling and state management as required
   * by the technical specifications.
   */
  private fun checkInitialState() {
    checkInitialStateJob = viewModelScope.launch {
      _viewState.update {
        SplashViewState.Loading
      }
      val isNetworkAvailable = checkNetworkAvailabilityUseCase()

      if (
        isNetworkAvailable is Result.Success
        && isNetworkAvailable.data
      ) {
        checkForCachedCity()
      } else {
        _viewState.update {
          SplashViewState.NavigateToNoInternet
        }
      }
    }
  }

  /**
   * Retrieves and processes the last searched city.
   * Implements proper error handling using Flow operators
   * and updates view state accordingly.
   */
  private suspend fun checkForCachedCity() {
    getLastSearchedCityUseCase().catch { err ->
      _viewState.update { SplashViewState.NavigateToCityInput }
    }
      .collectLatest { lastCity ->
        _viewState.update {
          if (lastCity != null) {
            SplashViewState.NavigateToCurrentWeather
          } else {
            SplashViewState.NavigateToCityInput
          }
        }
      }
  }
}
