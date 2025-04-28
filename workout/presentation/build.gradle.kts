plugins {
    alias(libs.plugins.momentum.android.feature.plugin)
}

android {
    namespace = "ge.fitness.workout.presentation"

}

dependencies {
    implementation(projects.core.presentation.designSystem)
    implementation(projects.core.presentation.ui)
    implementation(projects.core.domain)
}