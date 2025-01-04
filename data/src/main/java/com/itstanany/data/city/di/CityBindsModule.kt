package com.itstanany.data.city.di

import com.itstanany.data.city.local.CityLocalDataSource
import com.itstanany.data.city.local.PreferencesCityLocalDataSource
import com.itstanany.data.city.remote.CityRemoteApiService
import com.itstanany.data.city.remote.CityRemoteDataSource
import com.itstanany.data.city.remote.CityRemoteDataSourceImpl
import com.itstanany.data.city.repositories.CityRepositoryImpl
import com.itstanany.data.network.di.ForecastRetrofit
import com.itstanany.domain.city.repositories.CityRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class CityBindsModule {

  @Binds
  abstract fun bindCityRepository(
    cityRepositoryImpl: CityRepositoryImpl,
  ): CityRepository

  @Binds
  abstract fun bindCityLocalDataSource(
    cityLocalDataSourceImpl: PreferencesCityLocalDataSource,
  ): CityLocalDataSource


  @Binds
  abstract fun bindCityRemoteDataSource(
    cityRemoteDataSourceImpl: CityRemoteDataSourceImpl,
  ): CityRemoteDataSource

}
