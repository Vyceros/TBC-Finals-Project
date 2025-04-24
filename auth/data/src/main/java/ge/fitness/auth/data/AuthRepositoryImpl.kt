package ge.fitness.auth.data

import com.google.firebase.auth.FirebaseAuthException
import ge.fitness.auth.domain.AuthRepository
import ge.fitness.core.domain.User
import ge.fitness.core.domain.util.AuthError
import ge.fitness.core.domain.util.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseAuthDataSource
) : AuthRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Resource<User, AuthError.LoginError> {
        return try {
            Resource.Loading
            val user = dataSource.signIn(email, password)
            Resource.Success(user)
        } catch (e: FirebaseAuthException) {
            Resource.Error(mapFirebaseLoginError(e))
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        fullName: String
    ): Resource<User, AuthError.RegisterError> {
        return try {
            Resource.Loading
            val user = dataSource.signUp(email, password, fullName)
            Resource.Success(user)
        } catch (e: FirebaseAuthException) {
            Resource.Error(mapFirebaseRegisterError(e))
        }
    }


}

private fun mapFirebaseLoginError(e: Throwable): AuthError.LoginError {
    val errorCode = (e as? FirebaseAuthException)?.errorCode
    val message = e.message?.lowercase() ?: ""

    return when {
        errorCode == "ERROR_USER_NOT_FOUND" || message.contains("no user record") -> AuthError.LoginError.USER_DOESNT_EXIST
        errorCode == "ERROR_INVALID_EMAIL" || message.contains("email") -> AuthError.LoginError.INVALID_EMAIL
        errorCode == "ERROR_WRONG_PASSWORD" || message.contains("password is invalid") || message.contains(
            "auth credential is incorrect"
        ) -> AuthError.LoginError.INVALID_PASSWORD

        else -> AuthError.LoginError.UNKNOWN
    }
}

private fun mapFirebaseRegisterError(e: Throwable): AuthError.RegisterError {
    val errorCode = (e as? FirebaseAuthException)?.errorCode
    val message = e.message?.lowercase() ?: ""

    return when {
        errorCode == "ERROR_EMAIL_ALREADY_IN_USE" || message.contains("email address is already") -> AuthError.RegisterError.USER_ALREADY_EXISTS
        errorCode == "ERROR_INVALID_EMAIL" || message.contains("email") -> AuthError.RegisterError.INVALID_EMAIL
        errorCode == "ERROR_WEAK_PASSWORD" || message.contains("password should be") -> AuthError.RegisterError.INVALID_PASSWORD
        else -> AuthError.RegisterError.UNKNOWN
    }
}
