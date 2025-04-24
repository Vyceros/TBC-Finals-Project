package ge.fitness.auth.presentation.signup

sealed interface SignUpEvent {
    data object Success : SignUpEvent
    data class ShowError(val error : Int?) : SignUpEvent
}