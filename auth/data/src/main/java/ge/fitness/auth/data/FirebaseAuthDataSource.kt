package ge.fitness.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import ge.fitness.core.domain.auth.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(
    private val auth: FirebaseAuth

) {
    suspend fun signIn(email: String, password: String): User {
        try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            return authResult.user?.toUser() ?: throw FirebaseAuthException(
                LOGIN_ERROR,
                LOGIN_ERROR
            )
        } catch (ex: Throwable) {
            throw ex
        }
    }

    suspend fun signUp(email: String, password: String, fullName: String): User {
        try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build()
            authResult?.user?.updateProfile(profileUpdates)
            return authResult.user?.toUser() ?: throw FirebaseAuthException(
                REGISTER_ERROR,
                REGISTER_ERROR
            )
        } catch (ex: Throwable) {
            throw ex
        }
    }

    suspend fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    fun getCurrentUser(): User? {
        return auth.currentUser?.toUser()
    }

    fun signOut() {
        auth.signOut()
    }

    companion object {
        const val LOGIN_ERROR = "login_error"
        const val REGISTER_ERROR = "register_error"
    }

}

private fun FirebaseUser.toUser(): User {
    return User(
        id = uid,
        email = email,
        isEmailVerified = isEmailVerified,
        displayName = displayName
    )
}