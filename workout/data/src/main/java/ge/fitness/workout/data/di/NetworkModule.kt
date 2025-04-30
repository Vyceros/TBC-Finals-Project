package ge.fitness.workout.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.fitness.core.data.util.BackupApi
import ge.fitness.workout.data.api.ExerciseApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideExerciseApi(@BackupApi retrofit: Retrofit): ExerciseApi {
        return retrofit.create(ExerciseApi::class.java)
    }
}