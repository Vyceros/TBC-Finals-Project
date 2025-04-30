package ge.fitness.workout.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.fitness.core.domain.datastore.DataStoreHelper
import ge.fitness.core.domain.util.Resource
import ge.fitness.core.domain.workout.Article
import ge.fitness.core.domain.workout.ArticleRepository
import ge.fitness.core.domain.workout.Exercise
import ge.fitness.core.domain.workout.ExerciseRepository
import ge.fitness.workout.domain.usecase.SyncArticleUseCase
import ge.fitness.workout.domain.usecase.SyncWorkoutUseCase
import ge.fitness.workout.presentation.model.ArticleUiModel
import ge.fitness.workout.presentation.model.ExerciseUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreHelper: DataStoreHelper,
    private val syncWorkoutUseCase: SyncWorkoutUseCase,
    private val exerciseRepository: ExerciseRepository,
    private val articleRepository: ArticleRepository,
    private val syncArticleUseCase: SyncArticleUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set


    init {
        fetchAndSyncExercises()
        fetchAndSyncArticles()
    }

    private fun fetchAndSyncExercises() {
        viewModelScope.launch(Dispatchers.IO) {
            syncWorkoutUseCase.invoke().collect {}
            exerciseRepository.getAllExercises().collect { res ->
                when (res) {
                    is Resource.Error -> {

                    }

                    Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        state = state.copy(
                            exercises = res.data.map { it.toUiModel() },
                            topWorkout = res.data.map { it.toUiModel() }.first()
                        )
                    }
                }

            }
        }
    }

    private fun fetchAndSyncArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            syncArticleUseCase.invoke().collect {}
            articleRepository.getAllArticles().collect { res ->
                when (res) {
                    is Resource.Error -> {

                    }

                    Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        state = state.copy(articles = res.data.map { it.toUiModel() }

                        )
                    }
                }

            }
        }
    }


}

fun Exercise.toUiModel(): ExerciseUiModel {
    return ExerciseUiModel(
        name = name,
        image = gifUrl
    )
}

fun Article.toUiModel(): ArticleUiModel {
    return ArticleUiModel(
        title = title,
        description = description,
        urlToImage = urlToImage
    )
}
