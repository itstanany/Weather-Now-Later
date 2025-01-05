package com.itstanany.features.city_input.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.usecases.SaveLastSearchedCityUseCase
import com.itstanany.domain.city.usecases.SearchCitiesUseCase
import com.itstanany.domain.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the City Input screen.
 *
 * This ViewModel manages the state and logic for searching cities, handling user input,
 * and saving the last searched city. It interacts with the [SearchCitiesUseCase] to fetch
 * city search results and the [SaveLastSearchedCityUseCase] to persist the selected city.
 *
 * @property searchCitiesUseCase The use case for searching cities based on a query.
 * @property saveLastSearchedCityUseCase The use case for saving the last searched city.
 */
@HiltViewModel
class CityInputViewModel @Inject constructor(
  private val searchCitiesUseCase: SearchCitiesUseCase,
  private val saveLastSearchedCityUseCase: SaveLastSearchedCityUseCase,
) : ViewModel() {
  /**
   * The internal mutable state flow for the ViewModel's state.
   */
  private val _viewState = MutableStateFlow(CityInputViewState())

  /**
   * The public immutable state flow representing the current state of the ViewModel.
   */
  val viewState = _viewState.asStateFlow()

  /**
   * Job for managing the search query collection flow.
   */
  private var searchQueryCollectionJob: Job? = null

  /**
   * Handles changes in the search query input.
   *
   * This method updates the search query in the state and triggers a search operation
   * if the query length is at least 3 characters. The search is debounced to avoid
   * excessive network requests.
   *
   * @param query The new search query entered by the user.
   */
  @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
  fun handleSearchQueryChanged(query: String) {
    _viewState.update { it.copy(searchQuery = query) }

    if (searchQueryCollectionJob == null) {
      searchQueryCollectionJob = viewModelScope.launch {
        _viewState.map { it.searchQuery }
          .distinctUntilChanged()
          .filter { it.length >= 3 }
          .debounce(250)
          .flatMapLatest { searchQuery ->
            flow {
              _viewState.update {
                it.copy(
                  isLoading = true,
                  searchResults = persistentListOf(),
                  isSearchResultsEmpty = false
                )
              }
              emit(searchCitiesUseCase(searchQuery))
            }
          }
          .collect { result ->
            when (result) {
              is Result.Success -> {
                _viewState.update {
                  it.copy(
                    searchResults = result.data.toImmutableList(),
                    isSearchResultsEmpty = result.data.isEmpty(),
                    isLoading = false,
                    errorMessage = null
                  )
                }
              }

              is Result.Failure -> {
                _viewState.update {
                  it.copy(
                    errorMessage = result.error.localizedMessage ?: "Unknown error",
                    isLoading = false
                  )
                }
              }
            }
          }
      }
    }
  }

  /**
   * Handles the selection of a city from the search results.
   *
   * This method saves the selected city using the [SaveLastSearchedCityUseCase]
   * and updates the state to indicate that a city has been selected.
   *
   * @param city The selected city.
   */
  fun handleSearchResultSelected(city: City) {
    viewModelScope.launch {
      saveLastSearchedCityUseCase(city)
      _viewState.update {
        it.copy(
          isSearchResultSelected = true,
        )
      }
    }
  }

  /**
   * Handles the completion of the search result selection action.
   *
   * This method resets the selection state and cancels the search query collection job.
   */
  fun handleSearchResultSelectionActionCompletion() {
    _viewState.update {
      it.copy(
        isSearchResultSelected = false,
      )
    }
    searchQueryCollectionJob?.cancel()
  }
}
