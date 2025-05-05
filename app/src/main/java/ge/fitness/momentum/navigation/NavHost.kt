package ge.fitness.momentum.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import ge.fitness.auth.presentation.navigation.AuthRoutes
import ge.fitness.auth.presentation.navigation.authGraph
import ge.fitness.auth.presentation.navigation.navigateToLogin
import ge.fitness.auth.presentation.navigation.navigateToSignUp
import ge.fitness.core.data.util.ConnectivityManager
import ge.fitness.core.domain.datastore.DataStoreHelper
import ge.fitness.core.domain.datastore.DataStoreKeys
import ge.fitness.momentum.R
import ge.fitness.momentum.ui.rememberMomentumAppState
import ge.fitness.workout.presentation.navigation.BottomNavItem
import ge.fitness.workout.presentation.navigation.WorkoutRoutes
import ge.fitness.workout.presentation.navigation.bottomNavItems
import ge.fitness.workout.presentation.navigation.navigateToHome
import ge.fitness.workout.presentation.navigation.navigateToSettings
import ge.fitness.workout.presentation.navigation.workoutGraph


@Composable
fun MomentumNavHost(
    connectivityManager: ConnectivityManager,
    dataStoreHelper: DataStoreHelper
) {
    val appState = rememberMomentumAppState(
        connectivityManager = connectivityManager
    )

    val navController = appState.navController
    val snackbarHostState = remember { SnackbarHostState() }

    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    val isLoggedIn by dataStoreHelper.getPreference(DataStoreKeys.IsLoggedIn)
        .collectAsStateWithLifecycle(initialValue = false)

    val rememberMe by dataStoreHelper.getPreference(DataStoreKeys.RememberMe)
        .collectAsStateWithLifecycle(initialValue = false)

    val offlineMessage = stringResource(R.string.you_are_offline_some_features_may_not_be_available)
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = offlineMessage,
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(WorkoutRoutes.Home::class.qualifiedName!!) {
                popUpTo(AuthRoutes.Login::class.qualifiedName!!) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val showBottomBar by remember(navBackStackEntry) {
        mutableStateOf(
            navBackStackEntry?.destination?.route in listOf(
                WorkoutRoutes.Home::class.qualifiedName,
                WorkoutRoutes.Settings::class.qualifiedName
            )
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                MomentumBottomNavigation(navController = navController)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            (if (isLoggedIn && rememberMe) {
                WorkoutRoutes.Home::class.qualifiedName
            } else AuthRoutes.Intro::class.qualifiedName)?.let {
                NavHost(
                    navController = navController,
                    startDestination = it
                ) {
                    authGraph(
                        onNavigateLogin = navController::navigateToLogin,
                        onNavigateRegister = navController::navigateToSignUp,
                        onNavigateHome = navController::navigateToHome,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        snakcbarHostState = snackbarHostState
                    )
                    workoutGraph(
                        onLogout = {
                            navController.navigateToLogin()
                        },
                        snakcbarHostState = snackbarHostState
                    )
                }
            }
        }
    }
}

@Composable
fun MomentumBottomNavigation(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.routeName

            NavigationBarItem(
                selected = selected,
                onClick = {
                    when (item) {
                        BottomNavItem.Home -> navController.navigateToHome()
                        BottomNavItem.Settings -> navController.navigateToSettings()
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) }
            )
        }
    }
}

