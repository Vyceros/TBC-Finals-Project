package ge.fitness.core.domain.datastore

import ge.fitness.core.domain.model.PreferenceKey
import kotlinx.coroutines.flow.Flow

interface DataStoreHelper {
    fun <T> getPreference(key : PreferenceKey<T>) : Flow<T>

    suspend fun <T> addPreference(key : PreferenceKey<T>, value : T)

    suspend fun <T>removePreference(key : PreferenceKey<T>)

    suspend fun clearAllPreferences()

}