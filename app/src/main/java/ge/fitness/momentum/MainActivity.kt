package ge.fitness.momentum

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import ge.fitness.auth.domain.auth.AuthManager
import ge.fitness.core.data.util.ConnectivityManager
import ge.fitness.core.presentation.design_system.theme.MomentumTheme
import ge.fitness.momentum.navigation.MomentumNavHost
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var isAppReady by mutableStateOf(false)
        splashScreen.setKeepOnScreenCondition { !isAppReady }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Sync Firebase and AuthManager
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null && !authManager.isUserLoggedIn()) {
            lifecycleScope.launch {
                try {
                    firebaseUser.getIdToken(false).await().token?.let {
                        authManager.saveUserSession(it)
                    }
                } catch (e: Exception) {
                    FirebaseAuth.getInstance().signOut()
                    authManager.logout()
                }
            }
        } else if (firebaseUser == null && authManager.isUserLoggedIn()) {
            authManager.logout()
        }

        lifecycleScope.launch {
            delay(1000)
            isAppReady = true
        }

        splashScreen.setOnExitAnimationListener { splashView ->
            ObjectAnimator.ofFloat(splashView.view, View.ALPHA, 1f, 0f).apply { duration = 500 }
                .also { it.start() }
            ObjectAnimator.ofFloat(splashView.view, View.SCALE_X, 1f, 1.5f).apply { duration = 500 }
                .also { it.start() }
            ObjectAnimator.ofFloat(splashView.view, View.SCALE_Y, 1f, 1.5f).apply { duration = 500 }
                .also { it.start() }
            splashView.view.postDelayed({ splashView.remove() }, 500)
        }

        setContent {
            val navController = rememberNavController()
            MomentumTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MomentumNavHost(
                        connectivityManager = connectivityManager,
                        authManager = authManager,
                        navController = navController
                    )
                }
            }
        }
    }
}
