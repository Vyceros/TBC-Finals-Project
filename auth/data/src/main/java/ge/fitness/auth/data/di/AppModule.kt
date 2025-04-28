package ge.fitness.auth.data.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.fitness.auth.data.AuthManagerImpl
import ge.fitness.auth.data.AuthRepositoryImpl
import ge.fitness.auth.data.FirebaseAuthDataSource
import ge.fitness.auth.data.GoogleSignInManagerImpl
import ge.fitness.auth.domain.auth.AuthManager
import ge.fitness.auth.domain.auth.AuthRepository
import ge.fitness.auth.domain.auth.GoogleSignInManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

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
        fun provideDataSource(auth: FirebaseAuth): FirebaseAuthDataSource {
            return FirebaseAuthDataSource(auth)
        }
    }

    @Binds
    @Singleton
    abstract fun bindGoogleSignInManager(
        impl: GoogleSignInManagerImpl
    ): GoogleSignInManager

    @Binds
    @Singleton
    abstract fun bindAuthManager(
        authManagerImpl: AuthManagerImpl
    ): AuthManager
}
