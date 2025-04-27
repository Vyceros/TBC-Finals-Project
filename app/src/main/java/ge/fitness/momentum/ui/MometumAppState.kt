package ge.fitness.momentum.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ge.fitness.core.data.util.ConnectivityManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Composable
fun rememberMomentumAppState(
    connectivityManager: ConnectivityManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): MomentumAppState {
    return remember(
        navController,
        coroutineScope,
        connectivityManager,

    ) {
        MomentumAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            connectivityManager = connectivityManager,
        )
    }
}

class MomentumAppState(
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
    connectivityManager: ConnectivityManager,
) {
    val isOffline = connectivityManager.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun showSnackbar(
        snackbarHostState: SnackbarHostState,
        message: String,
        actionLabel: String? = null
    ) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel
            )
        }
    }
}