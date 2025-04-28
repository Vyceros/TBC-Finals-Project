package ge.fitness.core.domain.auth

data class User(
    val id : String,
    val displayName : String?,
    val isEmailVerified : Boolean?,
    val email : String?,
)
