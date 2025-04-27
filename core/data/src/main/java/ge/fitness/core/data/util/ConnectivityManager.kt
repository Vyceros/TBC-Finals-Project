package ge.fitness.core.data.util

import kotlinx.coroutines.flow.Flow

interface ConnectivityManager {
    val isOnline: Flow<Boolean>
}