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

@HiltViewModel
class CityInputViewModel @Inject constructor(
  private val searchCitiesUseCase: SearchCitiesUseCase,
  private val saveLastSearchedCityUseCase: SaveLastSearchedCityUseCase,
) : ViewModel() {
  private val _viewState = MutableStateFlow(CityInputViewState())
  val viewState = _viewState.asStateFlow()

  private var searchQueryCollectionJob: Job? = null


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

  fun handleSearchResultSelectionActionCompletion() {
    _viewState.update {
      it.copy(
        isSearchResultSelected = false,
      )
    }
    searchQueryCollectionJob?.cancel()
  }
}
