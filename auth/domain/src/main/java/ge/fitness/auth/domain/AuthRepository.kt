package ge.fitness.auth.domain

import ge.fitness.core.domain.User
import ge.fitness.core.domain.util.AuthError
import ge.fitness.core.domain.util.Resource

interface AuthRepository {
    suspend fun signIn(
        email: String,
        password: String
    ): Resource<User, AuthError.LoginError>

    suspend fun signUp(
        email: String,
        password: String,
        fullName : String
    ): Resource<User, AuthError.RegisterError>
}