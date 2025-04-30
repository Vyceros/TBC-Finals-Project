plugins {
    alias(libs.plugins.momentum.application.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.momentum.hilt)
}

android {
    namespace = "ge.fitness.workout.data"

}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.squareup.retrofit)
    implementation(projects.core.domain)
    implementation(projects.workout.domain)
    implementation(projects.core.data)
    implementation(projects.core.database)
}