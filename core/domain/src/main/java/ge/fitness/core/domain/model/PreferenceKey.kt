package ge.fitness.core.domain.model

interface PreferenceKey<T> {
    val name : String
    val defaultValue : T
}