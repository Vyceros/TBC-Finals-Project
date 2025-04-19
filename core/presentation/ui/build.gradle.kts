plugins {
    alias(libs.plugins.momentum.application.library.compose)
}

android {
    namespace = "ge.fitness.core.presentation.ui"

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}