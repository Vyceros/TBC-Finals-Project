package ge.fitness.auth.domain.auth

import ge.fitness.core.domain.auth.User
import ge.fitness.core.domain.auth.AuthError
import ge.fitness.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow


class LoginUseCase(
    private val authRepo: AuthRepository
) {
     suspend operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<User, AuthError.LoginError>> {
        return authRepo.signIn(email, password)
    }


}