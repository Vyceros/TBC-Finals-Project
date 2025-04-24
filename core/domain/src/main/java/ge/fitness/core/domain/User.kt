package ge.fitness.core.domain

data class User(
    val id : String,
    val displayName : String?,
    val isEmailVerified : Boolean?,
    val email : String?,

)
