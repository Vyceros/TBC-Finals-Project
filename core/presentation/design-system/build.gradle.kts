plugins {
    alias(libs.plugins.momentum.application.library.compose)
}

android {
    namespace = "ge.fitness.core.presentation.design_system"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    api(libs.androidx.material3)
}