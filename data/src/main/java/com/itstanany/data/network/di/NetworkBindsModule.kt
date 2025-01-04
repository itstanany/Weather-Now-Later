package com.itstanany.data.network.di

import com.itstanany.data.network.repositories.NetworkRepositoryImpl
import com.itstanany.domain.network.repositories.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkBindsModule {
  @Binds
  @Singleton
  abstract fun bindNetworkRepository(
    networkRepositoryImpl: NetworkRepositoryImpl,
  ): NetworkRepository
}
