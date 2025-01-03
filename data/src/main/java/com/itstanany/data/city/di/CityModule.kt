package com.itstanany.data.city.di

import com.itstanany.data.city.local.CityLocalDataSource
import com.itstanany.data.city.local.PreferencesCityLocalDataSource
import com.itstanany.data.city.repositories.CityRepositoryImpl
import com.itstanany.domain.city.repositories.CityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CityModule {

  @Binds
  abstract fun bindCityRepository(
    cityRepositoryImpl: CityRepositoryImpl,
  ): CityRepository

  @Binds
  abstract fun bindCityLocalDataSource(
    cityLocalDataSourceImpl: PreferencesCityLocalDataSource,
  ): CityLocalDataSource
}
