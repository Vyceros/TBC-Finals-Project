package ge.fitness.auth.domain.auth

import ge.fitness.core.domain.auth.User
import ge.fitness.core.domain.auth.AuthError
import ge.fitness.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow


class SignUpUseCase(
    private val authRepo: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        fullName: String
    ): Flow<Resource<User, AuthError.RegisterError>> {
        return authRepo.signUp(email = email, password = password, fullName = fullName)
    }


}