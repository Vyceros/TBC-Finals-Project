package ge.fitness.auth.domain.auth

import ge.fitness.core.domain.auth.User
import ge.fitness.core.domain.auth.AuthError
import ge.fitness.core.domain.auth.GoogleUser
import ge.fitness.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<User, AuthError.LoginError>>

    suspend fun signInWithGoogle(
        idToken: String
    ): Flow<Resource<GoogleUser, AuthError.LoginError>>

    suspend fun signUp(
        email: String,
        password: String,
        fullName: String
    ): Flow<Resource<User, AuthError.RegisterError>>
}