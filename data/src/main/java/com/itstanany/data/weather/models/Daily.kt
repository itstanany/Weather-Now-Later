package com.itstanany.data.weather.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Daily(
    @SerialName("apparent_temperature_max")
    val apparentTemperatureMax: List<Double?>?,
    @SerialName("apparent_temperature_min")
    val apparentTemperatureMin: List<Double?>?,
    @SerialName("sunrise")
    val sunrise: List<String?>?,
    @SerialName("sunset")
    val sunset: List<String?>?,
    @SerialName("temperature_2m_max")
    val temperature2mMax: List<Double?>?,
    @SerialName("temperature_2m_min")
    val temperature2mMin: List<Double?>?,
    @SerialName("time")
    val time: List<String?>?,
    @SerialName("weather_code")
    val weatherCode: List<Int?>?,
    @SerialName("wind_direction_10m_dominant")
    val windDirection10mDominant: List<Int?>?,
    @SerialName("wind_speed_10m_max")
    val windSpeed10mMax: List<Double?>?
)