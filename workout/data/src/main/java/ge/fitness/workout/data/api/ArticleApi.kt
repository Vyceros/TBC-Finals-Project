package ge.fitness.workout.data.api

import ge.fitness.workout.data.model.ArticleDto
import retrofit2.http.GET

interface ArticleApi {
    @GET(ARTICLES)
    suspend fun getArticles(): List<ArticleDto>

    companion object{
        private const val ARTICLES = "c501cb8a-3494-43fd-8b33-d696151e0b0f"
    }
}