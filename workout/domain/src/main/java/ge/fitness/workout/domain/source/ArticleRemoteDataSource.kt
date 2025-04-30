package ge.fitness.workout.domain.source

import ge.fitness.core.domain.workout.Article

interface ArticleRemoteDataSource {
    suspend fun getArticles(): List<Article>
}