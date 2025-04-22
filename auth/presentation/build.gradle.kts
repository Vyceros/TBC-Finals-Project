plugins {
    alias(libs.plugins.momentum.android.feature.plugin)
    alias(libs.plugins.momentum.hilt)
}

android {
    namespace = "ge.fitness.auth.presentation"
}

dependencies {
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
}