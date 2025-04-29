package ge.fitness.core.data.util

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BackupApi