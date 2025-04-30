package ge.fitness.workout.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)