plugins {
    alias(libs.plugins.momentum.application.library)
    alias(libs.plugins.momentum.hilt)
}

android {
    namespace = "ge.fitness.core.database"

}

dependencies {
    implementation(projects.core.domain)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)

}