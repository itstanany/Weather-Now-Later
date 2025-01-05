package com.itstanany.features.splash.presentation.screen

import com.itstanany.domain.city.models.City
import com.itstanany.domain.city.usecases.GetLastSearchedCityUseCase
import com.itstanany.domain.common.Result
import com.itstanany.domain.network.usecases.CheckNetworkAvailabilityUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {
  @MockK
  private lateinit var getLastSearchedCityUseCase: GetLastSearchedCityUseCase

  @MockK
  private lateinit var checkNetworkAvailabilityUseCase: CheckNetworkAvailabilityUseCase

  private lateinit var viewModel: SplashViewModel

  private val testScheduler = TestCoroutineScheduler()
  private val testDispatcher = StandardTestDispatcher(testScheduler)

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(testDispatcher)
    viewModel = SplashViewModel(
      getLastSearchedCityUseCase,
      checkNetworkAvailabilityUseCase
    )
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `when network available and city exists then navigates to current weather`() = runTest {
    // Given
    val city = City("London", "UK", 51.5074f, -0.1278f, 1)
    coEvery { checkNetworkAvailabilityUseCase() } returns Result.Success(true)
    every { getLastSearchedCityUseCase() } returns flowOf(city)

    // When
    viewModel.processIntent(SplashIntent.CheckInitialState)
    testScheduler.advanceUntilIdle()

    // Then
    assertEquals(SplashViewState.NavigateToCurrentWeather, viewModel.viewState.value)
  }

  @Test
  fun `when network available and no city exists then navigates to city input`() = runTest {
    // Given
    coEvery { checkNetworkAvailabilityUseCase() } returns Result.Success(true)
    every { getLastSearchedCityUseCase() } returns flowOf(null)

    // When
    viewModel.processIntent(SplashIntent.CheckInitialState)
    testScheduler.advanceUntilIdle()

    // Then
    assertEquals(SplashViewState.NavigateToCityInput, viewModel.viewState.value)
  }

  @Test
  fun `when network unavailable then navigates to no internet`() = runTest {
    // Given
    coEvery { checkNetworkAvailabilityUseCase() } returns Result.Success(false)

    // When
    viewModel.processIntent(SplashIntent.CheckInitialState)
    testScheduler.advanceUntilIdle()

    // Then
    assertEquals(SplashViewState.NavigateToNoInternet, viewModel.viewState.value)
  }

  @Test
  fun `when network check fails then navigates to no internet`() = runTest {
    // Given
    coEvery { checkNetworkAvailabilityUseCase() } returns Result.Failure(Exception("Network error"))

    // When
    viewModel.processIntent(SplashIntent.CheckInitialState)
    testScheduler.advanceUntilIdle()

    // Then
    assertEquals(SplashViewState.NavigateToNoInternet, viewModel.viewState.value)
  }

  @Test
  fun `when city check throws error then navigates to city input`() = runTest {
    // Given
    coEvery { checkNetworkAvailabilityUseCase() } returns Result.Success(true)
    every { getLastSearchedCityUseCase() } returns flow { throw Exception("Database error") }

    // When
    viewModel.processIntent(SplashIntent.CheckInitialState)
    testScheduler.advanceUntilIdle()

    // Then
    assertEquals(SplashViewState.NavigateToCityInput, viewModel.viewState.value)
  }

  @Test
  fun `when navigation finished then cancels job`() = runTest {
    // Given
    viewModel.processIntent(SplashIntent.CheckInitialState)

    // When
    viewModel.processIntent(SplashIntent.NavigationActionFinished)
    testScheduler.advanceUntilIdle()

    // Then
    assertEquals(SplashViewState.Loading, viewModel.viewState.value)
  }
}
