package ge.fitness.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.fitness.core.data.ConnectivityManagerImpl
import ge.fitness.core.data.datastore.DataStoreHelperImpl
import ge.fitness.core.data.util.ConnectivityManager
import ge.fitness.core.domain.datastore.DataStoreHelper
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class CoreDataModule {

    @Singleton
    @Binds
    abstract fun bindsConnectivityManager(connectivityManagerImpl: ConnectivityManagerImpl): ConnectivityManager

    @Singleton
    @Binds
    abstract fun bindsDataStoreHelper(dataStoreHelperImpl: DataStoreHelperImpl): DataStoreHelper

}