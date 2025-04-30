plugins {
    alias(libs.plugins.momentum.application.library)
    alias(libs.plugins.momentum.hilt)
}

android {
    namespace = "ge.fitness.core.data"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)

    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.squareup.retrofit.serializer)
    implementation(libs.squareup.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.datastore.preferences)

}