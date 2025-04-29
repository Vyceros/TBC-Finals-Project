package ge.fitness.core.database.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.fitness.core.database.FireStoreDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideDataSource(firestore: FirebaseFirestore): FireStoreDataSource {
        return FireStoreDataSource(firestore)
    }

}