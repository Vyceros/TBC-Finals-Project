package ge.fitness.auth.presentation.login

sealed interface LoginEvent {
    data object LoginSuccess : LoginEvent
    data class ShowError(val message: Int?) : LoginEvent
}
