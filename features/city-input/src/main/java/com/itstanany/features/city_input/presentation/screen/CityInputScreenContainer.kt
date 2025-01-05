package com.itstanany.features.city_input.presentation.screen

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itstanany.domain.city.models.City
import com.itstanany.features.city_input.presentation.components.CityInputTopBar
import com.itstanany.features.city_input.presentation.components.SearchBar
import com.itstanany.features.city_input.presentation.components.SearchResultsBody

@Composable
fun CityInputScreenContainer(
  viewModel: CityInputViewModel,
  onNavigateToCurrentWeather: () -> Unit,
  modifier: Modifier = Modifier
) {
  val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
  val latestViewModel = rememberUpdatedState(viewModel)
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  LaunchedEffect(viewState.isSearchResultSelected) {
    if (viewState.isSearchResultSelected) {
      onNavigateToCurrentWeather()
      latestViewModel.value.handleSearchResultSelectionActionCompletion()
    }
  }

  val onCitySelected = remember(viewModel) {
    { city: City ->
      viewModel.handleSearchResultSelected(city)
    }
  }

  val onNavigateUp = remember {
    {
      viewModel.handleSearchResultSelectionActionCompletion()
      backDispatcher?.onBackPressed()
      Unit
    }
  }

  val onClearClick = remember(viewModel) {
    { viewModel.handleSearchQueryChanged("") }
  }

  Surface(
    color = MaterialTheme.colorScheme.background,
    modifier = modifier.fillMaxSize()
  ) {
    Column(
      modifier = Modifier.fillMaxSize()
    ) {
      CityInputTopBar(
        onNavigateUp = onNavigateUp
      )

      SearchBar(
        query = viewState.searchQuery,
        onQueryChange = viewModel::handleSearchQueryChanged,
        onClearClick = onClearClick,
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
      )

      SearchResultsBody(
        isLoading = viewState.isLoading,
        searchResults = viewState.searchResults,
        onCityClick = onCitySelected,
        errorMessage = viewState.errorMessage,
        isSearchResultsEmpty = viewState.isSearchResultsEmpty,
        modifier = Modifier.fillMaxSize()
      )
    }
  }
}
