package com.itstanany.weathernowandlater.config

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.itstanany.weathernowandlater.BuildConfig.GEOCODING_API_URL
import com.itstanany.weathernowandlater.test.BuildConfig.FORECAST_API_URL
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NetworkConfigImplTest {
  private lateinit var networkConfig: NetworkConfigImpl

  @Before
  fun setup() {
    networkConfig = NetworkConfigImpl()
  }

  @Test
  fun testForecastApiBaseUrlMatchesBuildConfigValue() {
    assertEquals(FORECAST_API_URL, networkConfig.forecastApiBaseUrl)
  }

  @Test
  fun testCityApiBaseUrlMatchesBuildConfigValue() {
    assertEquals(GEOCODING_API_URL, networkConfig.cityApiBaseUrl)
  }

  @Test
  fun testForecastApiBaseUrlHasValidFormat() {
    val url = networkConfig.forecastApiBaseUrl
    assertTrue(url.startsWith("https://"))
    assertTrue(url.endsWith("/"))
    assertTrue(url.isNotEmpty())
  }

  @Test
  fun testCityApiBaseUrlHasValidFormat() {
    val url = networkConfig.cityApiBaseUrl
    assertTrue(url.startsWith("https://"))
    assertTrue(url.endsWith("/"))
    assertTrue(url.isNotEmpty())
  }
}

