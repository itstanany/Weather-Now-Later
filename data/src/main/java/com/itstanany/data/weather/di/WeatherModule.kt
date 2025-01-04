package com.itstanany.data.weather.di

import com.itstanany.data.network.di.ForecastRetrofit
import com.itstanany.data.weather.remote.WeatherRemoteApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {
  @Provides
  @Singleton
  fun provideWeatherRemoteApiService(
    @ForecastRetrofit retrofit: Retrofit,
  ): WeatherRemoteApiService {
    return retrofit.create(WeatherRemoteApiService::class.java)
  }
}
