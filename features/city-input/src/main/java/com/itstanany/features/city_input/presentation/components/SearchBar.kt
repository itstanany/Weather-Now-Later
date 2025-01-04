package com.itstanany.features.city_input.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchBar(
  query: String,
  onQueryChange: (String) -> Unit,
  onClearClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedTextField(
    value = query,
    onValueChange = onQueryChange,
    placeholder = { Text("Enter city name") },
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search"
      )
    },
    trailingIcon = {
      if (query.isNotEmpty()) {
        IconButton(onClick = onClearClick) {
          Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Clear search"
          )
        }
      }
    },
    singleLine = true,
    modifier = modifier
  )
}
