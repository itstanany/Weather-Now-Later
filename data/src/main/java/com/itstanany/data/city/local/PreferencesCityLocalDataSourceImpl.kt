package com.itstanany.data.city.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.itstanany.domain.city.models.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesCityLocalDataSource @Inject constructor(
  private val dataStore: DataStore<Preferences>,
) : CityLocalDataSource {

  override fun getLastSearchedCity(): Flow<City?> = dataStore.data
    .map { preferences ->
      preferences[PreferencesKeys.LAST_CITY]?.let { encoded: String ->
        Json.decodeFromString<City>(encoded)
      }
    }
    .catch {
      emit(null)
    }

  override suspend fun saveCity(city: City) {
    dataStore.edit { preferences ->
      preferences[PreferencesKeys.LAST_CITY] = Json.encodeToString(city)
    }
  }

  companion object {
    object PreferencesKeys {
      val LAST_CITY = stringPreferencesKey("last_searched_city")
    }
  }
}
