package ge.fitness.workout.presentation.home

sealed interface HomeAction {
    data class OnArticleClick(val id: String) : HomeAction
}