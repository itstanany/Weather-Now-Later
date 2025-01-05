package com.itstanany.features.current_weather.presentation.uicomponents

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorMessage(
  message: String,
  modifier: Modifier = Modifier
) {
  Text(
    text = message,
    color = MaterialTheme.colorScheme.error,
    modifier = modifier
  )
}
