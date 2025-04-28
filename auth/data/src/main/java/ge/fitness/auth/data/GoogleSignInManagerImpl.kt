package ge.fitness.auth.data

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import ge.fitness.auth.domain.auth.GoogleSignInManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleSignInManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auth: FirebaseAuth
) : GoogleSignInManager {
    private val googleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("178552592186-bdruh3nmeoulprtkb4imb7q52k968a98.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    override fun getSignInRequest(): GoogleSignInManager.SignInRequest {
        googleSignInClient.signOut()

        val intent = googleSignInClient.signInIntent
        return AndroidSignInRequest(intent)
    }
    override fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
    }

    override fun getIdTokenFromResult(data: Any?): String? {
        if (data !is Intent) {
            Log.w("GSI", "No Intent returned from launcher")
            return null
        }

        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            account?.idToken.also {
                Log.d("GSI", "idToken=${it}…")
            }
        } catch (e: ApiException) {
            Log.e("GSI", "Google sign-in error", e)
            null
        }
    }

    /**
     * Android-specific implementation of SignInRequest that wraps an Intent
     */
    private class AndroidSignInRequest(private val intent: Intent) : GoogleSignInManager.SignInRequest {
        @Suppress("UNCHECKED_CAST")
        override fun <T> asPlatformRequest(): T = intent as T
    }
}