package ge.fitness.momentum

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ge.fitness.auth.presentation.login.LoginScreen
import ge.fitness.auth.presentation.navigation.AppNavigation
import ge.fitness.auth.presentation.signup.SignUpScreen
import ge.fitness.core.presentation.design_system.theme.MomentumTheme
import ge.fitness.momentum.presentation.ui.util.AppPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        var isAppReady by mutableStateOf(false)
        splashScreen.setKeepOnScreenCondition { !isAppReady }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            delay(1000)
            isAppReady = true
        }

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val fadeOut = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.ALPHA,
                1f,
                0f
            )

            val scaleOut = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.SCALE_X,
                1f,
                1.5f
            )

            val scaleOutY = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.SCALE_Y,
                1f,
                1.5f
            )

            fadeOut.duration = 500L
            scaleOut.duration = 500L
            scaleOutY.duration = 500L

            fadeOut.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    splashScreenView.remove()
                }
            })

            fadeOut.start()
            scaleOut.start()
            scaleOutY.start()
        }

        setContent {
            MomentumTheme {
                MainContent()
            }
        }
    }

    @Composable
    fun MainContent() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavigation()
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier
        )
    }

    @AppPreview
    @Composable
    fun GreetingPreview() {
        MainContent()
    }
}