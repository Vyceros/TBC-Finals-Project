plugins {
    alias(libs.plugins.momentum.application.library)
    alias(libs.plugins.momentum.hilt)
}

android {
    namespace = "ge.fitness.core.data"

}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.datastore.preferences)
}