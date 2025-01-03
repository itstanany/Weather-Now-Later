package com.itstanany.core.coroutinedispatcher.di

import com.itstanany.core.coroutinedispatcher.DispatcherProviderImpl
import com.itstanany.domain.common.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {
  @Binds
  @Singleton
  abstract fun bindDispatcherProvider(
    dispatcherProviderImpl: DispatcherProviderImpl,
  ): DispatcherProvider
}
