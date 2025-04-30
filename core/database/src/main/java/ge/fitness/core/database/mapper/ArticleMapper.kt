package ge.fitness.core.database.mapper

import ge.fitness.core.database.entity.ArticleEntity
import ge.fitness.core.domain.workout.Article

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

fun List<Article>.toEntityList(): List<ArticleEntity> {
    return map { it.toEntity() }
}

fun List<ArticleEntity>.toDomainList(): List<Article> {
    return map { it.toDomain() }
}