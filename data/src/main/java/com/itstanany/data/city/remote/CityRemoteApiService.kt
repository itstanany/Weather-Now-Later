package com.itstanany.data.city.remote

import com.itstanany.data.city.models.SearchCitiesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CityRemoteApiService {
    @GET("/search")
    suspend fun searchCities(
      @Query("name") query: String,
      @Query("count") count: Int = 10,
      ): SearchCitiesResponse
}
