package ge.fitness.workout.data

import ge.fitness.core.domain.workout.Article
import ge.fitness.workout.data.api.ArticleApi
import ge.fitness.workout.data.model.toDomain
import ge.fitness.workout.domain.source.ArticleRemoteDataSource
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(
    private val api: ArticleApi
) : ArticleRemoteDataSource {
    override suspend fun getArticles(): List<Article> {
        val articles = api.getArticles().map { it.toDomain() }
        return articles
    }

}