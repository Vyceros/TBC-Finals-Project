package ge.fitness.core.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.fitness.core.data.ConnectivityManagerImpl
import ge.fitness.core.data.util.ConnectivityManager
import ge.fitness.core.domain.util.NetworkHandler
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CoreDataModule {

    @Singleton
    @Binds
    abstract fun bindsConnectivityManager(connectivityManagerImpl: ConnectivityManagerImpl): ConnectivityManager

    companion object {
        @Provides
        @Singleton
        fun providesNetworkHandler(): NetworkHandler {
            return NetworkHandler()
        }
    }
}