package com.itstanany.data.weather.di

import com.itstanany.data.weather.remote.WeatherRemoteDataSource
import com.itstanany.data.weather.remote.WeatherRemoteDataSourceImpl
import com.itstanany.data.weather.repositories.WeatherRepositoryImpl
import com.itstanany.domain.weather.repositories.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherBindsModule {
  @Binds
  abstract fun bindWeatherRemoteDataSource(
    weatherRemoteDataSourceImpl: WeatherRemoteDataSourceImpl,
  ): WeatherRemoteDataSource

  @Binds
  abstract fun bindWeatherRepository(
    weatherRepositoryImpl: WeatherRepositoryImpl,
  ): WeatherRepository
}
