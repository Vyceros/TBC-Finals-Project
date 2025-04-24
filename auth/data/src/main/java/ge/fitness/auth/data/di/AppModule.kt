package ge.fitness.auth.data.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.fitness.auth.data.AuthRepositoryImpl
import ge.fitness.auth.data.FirebaseAuthDataSource
import ge.fitness.auth.domain.AuthRepository
import ge.fitness.auth.domain.usecase.ValidateEmailUseCase
import ge.fitness.auth.domain.usecase.ValidateEmailUseCaseImpl
import ge.fitness.auth.domain.usecase.ValidateFullNameUseCase
import ge.fitness.auth.domain.usecase.ValidateFullNameUseCaseImpl
import ge.fitness.auth.domain.usecase.ValidatePasswordMatchUseCase
import ge.fitness.auth.domain.usecase.ValidatePasswordMatchUseCaseImpl
import ge.fitness.auth.domain.usecase.ValidatePasswordUseCase
import ge.fitness.auth.domain.usecase.ValidatePasswordUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindsValidateEmailUseCase(validateEmailImpl: ValidateEmailUseCaseImpl): ValidateEmailUseCase

    @Singleton
    @Binds
    abstract fun bindsValidatePasswordUseCase(validatePasswordImpl: ValidatePasswordUseCaseImpl): ValidatePasswordUseCase

    @Singleton
    @Binds
    abstract fun bindsValidatePasswordMatchUseCase(validatePasswordMatchImpl: ValidatePasswordMatchUseCaseImpl): ValidatePasswordMatchUseCase

    @Singleton
    @Binds
    abstract fun bindsValidateFullNameUseCase(validateFullNameImpl: ValidateFullNameUseCaseImpl): ValidateFullNameUseCase

    @Singleton
    @Binds
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    companion object {

        @Provides
        @Singleton
        fun providesFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        @Provides
        @Singleton
        fun provideDataSource(auth : FirebaseAuth) : FirebaseAuthDataSource{
            return FirebaseAuthDataSource(auth)
        }
    }
}
