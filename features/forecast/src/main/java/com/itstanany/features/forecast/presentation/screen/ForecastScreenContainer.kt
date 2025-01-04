package com.itstanany.features.forecast.presentation.screen

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itstanany.features.forecast.R
import com.itstanany.features.forecast.presentation.uicomponents.ForecastList
import com.itstanany.features.forecast.presentation.uicomponents.ForecastTopBar

@Composable
fun ForecastScreenContainer(
  viewModel: ForecastViewModel,
  modifier: Modifier = Modifier
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

  val onUpClick: () -> Unit = remember(backDispatcher) {
    {
      backDispatcher?.onBackPressed()
    }
  }

  LaunchedEffect(Unit) {
    viewModel.processIntent(ForecastIntent.ScreenOpened)
  }

  Surface(
    color = MaterialTheme.colorScheme.background,
    modifier = modifier.fillMaxSize()
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      ForecastTopBar(
        screenTitle = stringResource(id = R.string.forecast_title),
        onUpClick = onUpClick,
        modifier = Modifier.fillMaxWidth()
      )

      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
      ) {
        when {
          state.isLoading -> {
            CircularProgressIndicator(
              color = MaterialTheme.colorScheme.primary,
              modifier = Modifier.align(Alignment.Center)
            )
          }

          state.error != null -> {
            Text(
              text = state.error!!,
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.error,
              modifier = Modifier.align(Alignment.Center)
            )
          }

          else -> {
            ForecastList(
              forecasts = state.forecasts,
              modifier = Modifier.fillMaxSize()
            )
          }
        }
      }
    }
  }
}
