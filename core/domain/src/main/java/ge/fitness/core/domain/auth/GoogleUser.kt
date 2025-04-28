package ge.fitness.core.domain.auth

data class GoogleUser(
    val user: User,
    val token: String
)