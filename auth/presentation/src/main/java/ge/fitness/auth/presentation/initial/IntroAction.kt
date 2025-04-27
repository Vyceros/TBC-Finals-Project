package ge.fitness.auth.presentation.initial

sealed interface IntroAction {
    data object OnSignUpClick : IntroAction
    data object OnLoginClick : IntroAction
}