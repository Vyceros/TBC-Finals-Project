package ge.fitness.workout.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.fitness.core.domain.workout.ExerciseRepository
import ge.fitness.workout.data.ExerciseRepositoryImpl
import ge.fitness.workout.data.RemoteDataSourceImpl
import ge.fitness.workout.domain.source.WorkoutRemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): WorkoutRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindsExerciseRepository(exerciseRepositoryImpl: ExerciseRepositoryImpl): ExerciseRepository

}