package com.itstanany.features.city_input.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itstanany.domain.city.models.City
import kotlinx.collections.immutable.ImmutableList


@Composable
fun SearchResultsBody(
  isLoading: Boolean,
  searchResults: ImmutableList<City>,
  onCityClick: (City) -> Unit,
  errorMessage: String?,
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier) {
    when {
      isLoading -> {
        CircularProgressIndicator(
          modifier = Modifier.align(Alignment.Center)
        )
      }

      errorMessage != null -> {
        Text(
          text = errorMessage,
          color = MaterialTheme.colorScheme.error,
          modifier = Modifier
            .align(Alignment.Center)
            .padding(16.dp)
        )
      }

      searchResults.isNotEmpty() -> {
        LazyColumn {
          items(
            count = searchResults.size,
            key = { index -> searchResults[index].id }
          ) { cityIndex ->
            CitySearchResultItem(
              city = searchResults[cityIndex],
              onClick = { onCityClick(searchResults[cityIndex]) }
            )
          }
        }
      }
    }
  }
}
