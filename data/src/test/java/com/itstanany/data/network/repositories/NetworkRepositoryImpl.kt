package com.itstanany.data.network.repositories

import android.content.Context
import com.itstanany.core.network.NetworkUtils
import com.itstanany.domain.common.Result
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
//
//class NetworkRepositoryImplTest {
//  @MockK
//  private lateinit var context: Context
//
//  private lateinit var networkRepository: NetworkRepositoryImpl
//
//  @Before
//  fun setup() {
//    MockKAnnotations.init(this)
//    networkRepository = NetworkRepositoryImpl(context)
//  }
//
//  @Test
//  fun `isNetworkAvailable when network is connected then returns success with true`() {
//    // Given
//    mockkObject(NetworkUtils)
//    every { NetworkUtils.isNetworkAvailable(context) } returns true
//
//    // When
//    val result = networkRepository.isNetworkAvailable()
//
//    // Then
//    assertTrue(result is Result.Success)
//    assertTrue((result as Result.Success).data)
//    verify(exactly = 1) { NetworkUtils.isNetworkAvailable(context) }
//  }
//
//  @Test
//  fun `isNetworkAvailable when network is disconnected then returns success with false`() {
//    // Given
//    mockkObject(NetworkUtils)
//    every { NetworkUtils.isNetworkAvailable(context) } returns false
//
//    // When
//    val result = networkRepository.isNetworkAvailable()
//
//    // Then
//    assertTrue(result is Result.Success)
//    assertFalse((result as Result.Success).data)
//    verify(exactly = 1) { NetworkUtils.isNetworkAvailable(context) }
//  }
//
//  @Test
//  fun `isNetworkAvailable when security exception occurs then returns failure`() {
//    // Given
//    mockkObject(NetworkUtils)
//    every {
//      NetworkUtils.isNetworkAvailable(context)
//    } throws SecurityException("Permission denied")
//
//    // When
//    val result = networkRepository.isNetworkAvailable()
//
//    // Then
//    assertTrue(result is Result.Failure)
//    assertTrue((result as Result.Failure).error is SecurityException)
//    verify(exactly = 1) { NetworkUtils.isNetworkAvailable(context) }
//  }
//
//  @After
//  fun tearDown() {
//    unmockkAll()
//  }
//}
