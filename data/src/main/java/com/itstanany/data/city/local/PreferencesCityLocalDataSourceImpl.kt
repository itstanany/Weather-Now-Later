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

/**
 * A local data source implementation for storing and retrieving city-related data
 * using [DataStore] for persistent storage.
 *
 * This class is annotated with [@Singleton] to ensure a single instance is used
 * across the application. It implements the [CityLocalDataSource] interface to provide
 * methods for saving and retrieving the last searched city.
 *
 * @property dataStore The [DataStore] instance used to store and retrieve preferences.
 */
@Singleton
class PreferencesCityLocalDataSource @Inject constructor(
  private val dataStore: DataStore<Preferences>,
) : CityLocalDataSource {

  /**
   * Retrieves the last searched city from the local data store.
   *
   * This method returns a [Flow] that emits the last searched city as a [City] object.
   * If no city is found or an error occurs, the flow emits `null`.
   *
   * @return A [Flow] emitting the last searched city or `null`.
   */
  override fun getLastSearchedCity(): Flow<City?> = dataStore.data
    .map { preferences ->
      preferences[PreferencesKeys.LAST_CITY]?.let { encoded: String ->
        Json.decodeFromString<City>(encoded)
      }
    }
    .catch {
      emit(null)
    }

  /**
   * Saves the provided city to the local data store.
   *
   * This method encodes the [City] object to a JSON string and stores it in the [DataStore].
   *
   * @param city The [City] object to be saved.
   */
  override suspend fun saveCity(city: City) {
    dataStore.edit { preferences ->
      preferences[PreferencesKeys.LAST_CITY] = Json.encodeToString(city)
    }
  }

  companion object {
    /**
     * Contains preference keys used for storing and retrieving data from [DataStore].
     */
    object PreferencesKeys {
      /**
       * Key for storing the last searched city as a JSON-encoded string.
       */
      val LAST_CITY = stringPreferencesKey("last_searched_city")
    }
  }
}
