package com.itstanany.data.city.di

import com.itstanany.data.city.remote.CityRemoteApiService
import com.itstanany.data.network.di.CityRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CityModule {

  @Provides
  @Singleton
  fun provideCityRemoteApiService(
    @CityRetrofit retrofit: Retrofit,
  ): CityRemoteApiService {
    return retrofit.create(CityRemoteApiService::class.java)
  }
}
