package com.itstanany.data.network.di

import com.itstanany.data.network.NetworkConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  @Provides
  @Singleton
  fun provideJson(): Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    isLenient = true
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      //todo remove magic numbers to follow best practices
      .connectTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .build()
  }

  @Provides
  @Singleton
  fun provideCityRetrofit(
    okHttpClient: OkHttpClient,
    json: Json,
    networkConfig: NetworkConfig,
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(networkConfig.cityApiBaseUrl)
      .client(okHttpClient)
      .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
      .build()
  }

  @Provides
  @Singleton
  fun provideForecastRetrofit(
    okHttpClient: OkHttpClient,
    json: Json,
    networkConfig: NetworkConfig,
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(networkConfig.forecastApiBaseUrl)
      .client(okHttpClient)
      .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
      .build()
  }
}
