plugins {
    alias(libs.plugins.momentum.application.library)
    alias(libs.plugins.momentum.hilt)

}

android {
    namespace = "ge.fitness.auth.data"
}

dependencies {

    implementation(libs.firebase.auth)
    implementation(platform(libs.firebase.bom))

    implementation(projects.core.data)
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
}