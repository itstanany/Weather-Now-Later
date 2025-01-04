package com.itstanany.weathernowandlater.di

import com.itstanany.data.network.NetworkConfig
import com.itstanany.weathernowandlater.config.NetworkConfigImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
  @Binds
  @Singleton
  abstract fun bindNetworkConfig(networkConfigImpl: NetworkConfigImpl): NetworkConfig
}
