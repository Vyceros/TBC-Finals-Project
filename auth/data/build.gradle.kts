plugins {
    alias(libs.plugins.momentum.application.library)
    alias(libs.plugins.momentum.hilt)

}

android {
    namespace = "ge.fitness.auth.data"
}

dependencies {
    // Add these to your app-level build.gradle dependencies
    implementation ("com.google.android.gms:play-services-auth:20.7.0")

    implementation(libs.firebase.auth)
    implementation(platform(libs.firebase.bom))

    implementation(projects.core.data)
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
}