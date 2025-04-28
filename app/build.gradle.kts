plugins {
    alias(libs.plugins.momentum.application.compose)
    alias(libs.plugins.momentum.hilt)
}

android {
    namespace = "ge.fitness.momentum"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(projects.core.presentation.ui)
    implementation(projects.core.presentation.designSystem)
    implementation(projects.core.data)
    implementation(projects.core.domain)

    implementation(projects.workout.presentation)

    implementation(projects.auth.domain)
    implementation(projects.auth.data)
    implementation(projects.auth.presentation)

}