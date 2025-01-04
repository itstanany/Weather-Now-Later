package com.itstanany.data.city.di

import com.itstanany.data.city.remote.CityRemoteApiService
import com.itstanany.data.network.di.ForecastRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object CityModule {
  @Provides
  fun provideCityRemoteApiService(
    @ForecastRetrofit retrofit: Retrofit,
  ): CityRemoteApiService {
    return retrofit.create(CityRemoteApiService::class.java)
  }
}
