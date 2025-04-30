package ge.fitness.workout.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.fitness.core.domain.workout.ExerciseRepository
import ge.fitness.workout.domain.source.WorkoutRemoteDataSource
import ge.fitness.workout.domain.usecase.SyncWorkoutUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesSyncUseCase(
        remoteDataSource: WorkoutRemoteDataSource,
        repository: ExerciseRepository
    ): SyncWorkoutUseCase {
        return SyncWorkoutUseCase(remoteDataSource = remoteDataSource, repository = repository)
    }

}