package ge.fitness.core.domain.model

import kotlinx.coroutines.flow.Flow

interface DataStoreHelper {
    fun <T> getPreference(key : PreferenceKey<T>) : Flow<T>

    suspend fun <T> addPreference(key : PreferenceKey<T>, value : T)

    suspend fun clearAllPreferences()

}