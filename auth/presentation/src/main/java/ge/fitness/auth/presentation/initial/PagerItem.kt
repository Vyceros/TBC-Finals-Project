package ge.fitness.auth.presentation.initial

data class PagerItem(
    val title: String,
    val icon : String,
    val backgroundImageRes: Int? = null,
    val isLastSlide: Boolean = false
)