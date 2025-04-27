plugins {
    alias(libs.plugins.momentum.android.feature.plugin)
}

android {
    namespace = "ge.fitness.onboarding.presentation"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.onboarding.domain)
}