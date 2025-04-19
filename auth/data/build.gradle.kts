plugins {
    alias(libs.plugins.momentum.application.compose)
}

android {
    namespace = "ge.fitness.auth.data"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
}