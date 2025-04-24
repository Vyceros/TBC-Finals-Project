plugins {
    alias(libs.plugins.momentum.application.library.compose)
}

android {
    namespace = "ge.fitness.core.presentation.ui"

}

dependencies {
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.compose.viewmodel)
}