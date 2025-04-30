package ge.fitness.workout.presentation.model

data class ArticleUiModel(
    val title: String,
    val description: String?,
    val urlToImage: String?,
    )

data class ArticleListUiModel(
    val articles: List<ArticleUiModel>,
)