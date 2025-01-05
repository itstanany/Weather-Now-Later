package com.itstanany.features.city_input.presentation.screen

import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.usecases.SaveLastSearchedCityUseCase
import com.itstanany.domain.city.usecases.SearchCitiesUseCase
import com.itstanany.domain.common.Result
import com.itstanany.features.city_input.presentation.screen.CityInputViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CityInputViewModelTest {
  @MockK
  private lateinit var searchCitiesUseCase: SearchCitiesUseCase

  @MockK
  private lateinit var saveLastSearchedCityUseCase: SaveLastSearchedCityUseCase

  private lateinit var viewModel: CityInputViewModel

  private val testScheduler = TestCoroutineScheduler()
  private val testDispatcher = StandardTestDispatcher(testScheduler)

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(testDispatcher)
    viewModel = CityInputViewModel(searchCitiesUseCase, saveLastSearchedCityUseCase)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `handleSearchQueryChanged when query length less than 3 then does not trigger search`() =
    runTest {
      // When
      viewModel.handleSearchQueryChanged("ab")

      // Then
      assertEquals("ab", viewModel.viewState.value.searchQuery)
      coVerify(exactly = 0) { searchCitiesUseCase(any()) }
    }

  @Test
  fun `handleSearchQueryChanged when query valid then updates state with results`() = runTest {
    // Given
    val query = "London"
    val cities = listOf(City("London", "UK", 51.5074f, -0.1278f, 1))
    coEvery { searchCitiesUseCase(query) } returns Result.Success(cities)

    // When
    viewModel.handleSearchQueryChanged(query)
    testScheduler.advanceTimeBy(300) // Account for debounce

    // Then
    with(viewModel.viewState.value) {
      assertEquals(query, searchQuery)
      assertEquals(cities, searchResults)
      assertFalse(isLoading)
      assertFalse(isSearchResultsEmpty)
      assertNull(errorMessage)
    }
  }

  @Test
  fun `handleSearchQueryChanged when search fails then updates error state`() = runTest {
    // Given
    val query = "London"
    val error = Exception("Network error")
    coEvery { searchCitiesUseCase(query) } returns Result.Failure(error)

    // When
    viewModel.handleSearchQueryChanged(query)
    testScheduler.advanceTimeBy(300)

    // Then
    with(viewModel.viewState.value) {
      assertEquals(query, searchQuery)
      assertTrue(searchResults.isEmpty())
      assertFalse(isLoading)
      assertEquals("Network error", errorMessage)
    }
  }

  @Test
  fun `handleSearchResultSelected when city selected then saves city and updates state`() =
    runTest {
      // Given
      val city = City("London", "UK", 51.5074f, -0.1278f, 1)
      coEvery { saveLastSearchedCityUseCase(city) } returns Result.Success(Unit)

      // When
      viewModel.handleSearchResultSelected(city)
      testScheduler.advanceUntilIdle()

      // Then
      assertTrue(viewModel.viewState.value.isSearchResultSelected)
      coVerify(exactly = 1) { saveLastSearchedCityUseCase(city) }
    }

  @Test
  fun `handleSearchResultSelectionActionCompletion resets selection state and cancels job`() =
    runTest {
      val city = City("London", "UK", 51.5074f, -0.1278f, 1)
      coEvery { saveLastSearchedCityUseCase(city) } returns Result.Success(Unit)

      // Given
      viewModel.handleSearchQueryChanged("London")
      viewModel.handleSearchResultSelected(city)

      // When
      viewModel.handleSearchResultSelectionActionCompletion()

      // Then
      assertFalse(viewModel.viewState.value.isSearchResultSelected)
    }

  @Test
  fun `handleSearchQueryChanged debounces rapid queries`() = runTest {
    // Given
    val query = "London"
    coEvery { searchCitiesUseCase(query) } returns Result.Success(emptyList())

    // When
    viewModel.handleSearchQueryChanged("Lon")
    viewModel.handleSearchQueryChanged("Lond")
    viewModel.handleSearchQueryChanged(query)
    testScheduler.advanceTimeBy(200)

    // Then
    coVerify(exactly = 0) { searchCitiesUseCase(any()) }

    testScheduler.advanceTimeBy(100)
    coVerify(exactly = 1) { searchCitiesUseCase(query) }
  }
}
