package ge.fitness.core.data.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import ge.fitness.core.domain.model.PreferenceKey

@Suppress("UNCHECKED_CAST")
fun <T> mapPreferenceKey(key : PreferenceKey<T>) : Preferences.Key<T> {
    return when(key.defaultValue){
        is Boolean -> booleanPreferencesKey(key.name) as Preferences.Key<T>
        is String -> stringPreferencesKey(key.name) as Preferences.Key<T>
        else -> {
            throw IllegalStateException("Such key doenst exist ")
        }
    }
}