plugins {
    alias(libs.plugins.momentum.android.feature.plugin)
}

android {
    namespace = "ge.fitness.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
}