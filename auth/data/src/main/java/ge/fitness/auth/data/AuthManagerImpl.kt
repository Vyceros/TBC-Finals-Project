package ge.fitness.auth.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ge.fitness.auth.domain.auth.AuthManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthManager {

    private val sharedPreferences = context.getSharedPreferences("momentum_auth_prefs", Context.MODE_PRIVATE)

    companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_USER_TOKEN = "user_token"
    }

    override fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    override fun logout() {
        sharedPreferences.edit()
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .putString(KEY_USER_TOKEN, null)
            .apply()
    }

    override fun saveUserSession(token: String) {
        sharedPreferences.edit()
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .putString(KEY_USER_TOKEN, token)
            .apply()
    }

    override fun getUserToken(): String? {
        return sharedPreferences.getString(KEY_USER_TOKEN, null)
    }
}