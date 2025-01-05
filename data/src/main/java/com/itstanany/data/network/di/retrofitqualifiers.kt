package com.itstanany.data.network.di

import javax.inject.Qualifier

/**
 * A qualifier annotation used to identify the Retrofit instance for weather forecast API requests.
 *
 * This annotation is used to distinguish between different Retrofit instances when injecting
 * dependencies. It helps to ensure that the correct Retrofit instance is used for making
 * weather forecast API requests.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ForecastRetrofit

/**
 * A qualifier annotation used to identify the Retrofit instance for city API requests.
 *
 * This annotation is used to distinguish between different Retrofit instances when injecting
 * dependencies. It helps to ensure that the correct Retrofit instance is used for making
 * city API requests.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CityRetrofit
