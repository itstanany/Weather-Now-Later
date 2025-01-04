package com.itstanany.data.city.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchCitiesResponse(
    @SerialName("results")
    val results: List<CityResultDto>
)
