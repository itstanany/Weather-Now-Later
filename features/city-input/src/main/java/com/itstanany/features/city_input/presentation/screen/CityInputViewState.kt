package com.itstanany.features.city_input.presentation.screen

import com.itstanany.domain.city.models.City
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Represents the UI state for the City Input screen.
 *
 * This data class holds all the necessary state properties required to render the City Input screen,
 * including search results, loading states, error messages, and user input.
 *
 * @property searchResults The list of cities returned from the search operation. Defaults to an empty immutable list.
 * @property isLoading Indicates whether a search operation is in progress. Defaults to `false`.
 * @property errorMessage The error message to display if a search operation fails. Defaults to `null`.
 * @property searchQuery The current search query entered by the user. Defaults to an empty string.
 * @property isSearchResultSelected Indicates whether a city has been selected from the search results. Defaults to `false`.
 * @property isSearchResultsEmpty Indicates whether the search results are empty. Defaults to `false`.
 */
data class CityInputViewState(
  val searchResults: ImmutableList<City> = persistentListOf(),
  val isLoading: Boolean = false,
  val errorMessage: String? = null,
  val searchQuery: String = "",
  val isSearchResultSelected: Boolean = false,
  val isSearchResultsEmpty: Boolean = false,
)
