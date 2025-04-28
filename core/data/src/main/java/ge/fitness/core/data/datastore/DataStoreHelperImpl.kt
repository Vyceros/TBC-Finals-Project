package ge.fitness.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import ge.fitness.core.domain.model.DataStoreHelper
import ge.fitness.core.domain.model.PreferenceKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreHelperImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    DataStoreHelper {
    override fun <T> getPreference(key: PreferenceKey<T>): Flow<T> {
        return dataStore.data.map { pref ->
            pref[mapPreferenceKey(key)] ?: key.defaultValue
        }
    }

    override suspend fun <T> addPreference(key: PreferenceKey<T>, value: T) {
        dataStore.edit { pref ->
            pref[mapPreferenceKey(key)] = value
        }
    }

    override suspend fun clearAllPreferences() {
        dataStore.edit { it.clear() }
    }
}