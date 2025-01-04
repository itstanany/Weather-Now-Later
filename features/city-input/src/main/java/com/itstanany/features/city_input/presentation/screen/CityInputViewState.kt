package com.itstanany.features.city_input.presentation.screen

import com.itstanany.domain.city.models.City
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class CityInputViewState(
  val searchResults: ImmutableList<City> = persistentListOf(),
  val isLoading: Boolean = false,
  val errorMessage: String? = null,
  val searchQuery: String = "",
  val isSearchResultSelected: Boolean = false,
  val isSearchResultsEmpty: Boolean = false,
)
