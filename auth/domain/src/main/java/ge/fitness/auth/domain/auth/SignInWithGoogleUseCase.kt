package ge.fitness.auth.domain.auth

import ge.fitness.core.domain.auth.AuthError
import ge.fitness.core.domain.auth.GoogleUser
import ge.fitness.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class SignInWithGoogleUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Flow<Resource<GoogleUser, AuthError.LoginError>> {
        return repository.signInWithGoogle(idToken)
    }
}