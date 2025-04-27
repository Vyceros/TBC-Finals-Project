package ge.fitness.auth.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.fitness.auth.domain.auth.AuthRepository
import ge.fitness.auth.domain.auth.LoginUseCase
import ge.fitness.auth.domain.auth.SignUpUseCase
import ge.fitness.auth.domain.validation.ValidateEmailUseCase
import ge.fitness.auth.domain.validation.ValidateFullNameUseCase
import ge.fitness.auth.domain.validation.ValidatePasswordMatchUseCase
import ge.fitness.auth.domain.validation.ValidatePasswordUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providesSignInUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun providesSignUpUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun providesValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase()
    }

    @Singleton
    @Provides
    fun providesValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }

    @Singleton
    @Provides
    fun providesValidatePasswordMatchUseCase(): ValidatePasswordMatchUseCase {
        return ValidatePasswordMatchUseCase()
    }

    @Singleton
    @Provides
    fun providesValidateFullNameUseCase(): ValidateFullNameUseCase {
        return ValidateFullNameUseCase()
    }


}