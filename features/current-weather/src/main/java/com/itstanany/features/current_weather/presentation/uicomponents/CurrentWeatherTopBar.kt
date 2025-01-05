package com.itstanany.features.current_weather.presentation.uicomponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.itstanany.domain.city.models.City


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherTopBar(
  city: City?,
  onSearchClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  TopAppBar(
    title = {
      Text(
        text = city?.let { "${it.name}, ${it.country}" } ?: "Weather Now"
      )
    },
    actions = {
      IconButton(onClick = onSearchClick) {
        Icon(
          imageVector = Icons.Default.Search,
          contentDescription = "Change city"
        )
      }
    },
    modifier = modifier
  )
}
