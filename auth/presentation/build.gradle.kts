plugins {
    alias(libs.plugins.momentum.android.feature.plugin)
}

android {
    namespace = "ge.fitness.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)

    implementation(libs.foundation.pager)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.auth.ktx)

    //Tests
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockk)
}