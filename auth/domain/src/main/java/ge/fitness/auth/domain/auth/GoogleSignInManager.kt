package ge.fitness.auth.domain.auth

/**
 * Domain interface for Google Sign-In functionality.
 * This keeps the domain layer independent of specific implementations.
 */
interface GoogleSignInManager {
    /**
     * Gets the sign-in request that can be used to start Google Sign-In process
     */
    fun getSignInRequest(): SignInRequest

    /**
     * Signs out the currently signed-in user
     */
    fun signOut()

    /**
     * Extracts the ID token from the result data returned from Google Sign-In
     * @param data The result data returned from the sign-in process
     * @return The Google ID token or null if sign-in failed
     */
    fun getIdTokenFromResult(data: Any?): String?

    /**
     * A platform-agnostic representation of a sign-in request
     * This allows the domain layer to remain independent of Android-specific components
     */
    interface SignInRequest {
        /**
         * Converts this request to a platform-specific type (handled by the implementation)
         */
        fun <T> asPlatformRequest(): T
    }
}