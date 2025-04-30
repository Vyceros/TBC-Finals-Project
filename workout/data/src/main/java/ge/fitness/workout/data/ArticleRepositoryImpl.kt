package ge.fitness.workout.data

import ge.fitness.core.database.FireStoreDataSource
import ge.fitness.core.database.mapper.toDomain
import ge.fitness.core.database.mapper.toEntity
import ge.fitness.core.domain.util.ApplicationError
import ge.fitness.core.domain.util.NetworkHandler
import ge.fitness.core.domain.util.Resource
import ge.fitness.core.domain.util.toNetworkError
import ge.fitness.core.domain.workout.Article
import ge.fitness.core.domain.workout.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val fireStoreDataSource: FireStoreDataSource,
    private val networkHandler: NetworkHandler
) : ArticleRepository {
    override suspend fun saveArticles(articles: List<Article>): Flow<Resource<Unit, ApplicationError.NetworkError>> {
        return networkHandler.wrapWithResourceFlow(
            errorMapper = { e -> e.toNetworkError() },
            block = { fireStoreDataSource.saveArticles(articles.map { it.toEntity() }) }
        )
    }

    override fun getAllArticles(): Flow<Resource<List<Article>, ApplicationError.NetworkError>> {
        return networkHandler.wrapWithResourceFlow(
            errorMapper = { e -> e.toNetworkError() },
            block = {
                fireStoreDataSource.getAllArticles().map { entities ->
                    entities.map { it.toDomain() }
                }.first()
            }
        )
    }
}