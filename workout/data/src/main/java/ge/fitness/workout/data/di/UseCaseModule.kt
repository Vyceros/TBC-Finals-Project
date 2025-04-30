package ge.fitness.workout.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.fitness.core.domain.workout.ArticleRepository
import ge.fitness.core.domain.workout.ExerciseRepository
import ge.fitness.workout.domain.source.ArticleRemoteDataSource
import ge.fitness.workout.domain.source.WorkoutRemoteDataSource
import ge.fitness.workout.domain.usecase.SyncArticleUseCase
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

    @Provides
    @Singleton
    fun provideArticleSyncUseCase(
        remoteDataSource: ArticleRemoteDataSource,
        repository : ArticleRepository
    ) : SyncArticleUseCase{
        return SyncArticleUseCase(remoteDataSource = remoteDataSource, repository = repository)
    }
}