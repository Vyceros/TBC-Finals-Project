package ge.fitness.workout.data.model

import ge.fitness.core.domain.workout.Article

fun ArticleDto.toDomain(): Article {
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