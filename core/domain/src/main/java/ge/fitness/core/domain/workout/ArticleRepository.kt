package ge.fitness.core.domain.workout

import ge.fitness.core.domain.util.ApplicationError
import ge.fitness.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun saveArticles(articles: List<Article>): Flow<Resource<Unit, ApplicationError.NetworkError>>
    fun getAllArticles(): Flow<Resource<List<Article>, ApplicationError.NetworkError>>
}