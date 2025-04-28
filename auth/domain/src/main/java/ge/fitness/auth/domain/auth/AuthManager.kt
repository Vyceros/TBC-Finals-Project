package ge.fitness.auth.domain.auth

interface AuthManager {
    fun isUserLoggedIn(): Boolean
    fun logout()
    fun saveUserSession(token: String)
    fun getUserToken(): String?
}