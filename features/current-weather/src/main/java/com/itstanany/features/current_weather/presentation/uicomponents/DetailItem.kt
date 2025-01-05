package com.itstanany.features.current_weather.presentation.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailItem(
  label: String,
  value: String,
  modifier: Modifier = Modifier
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp)
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodyLarge
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyLarge
    )
  }
}
