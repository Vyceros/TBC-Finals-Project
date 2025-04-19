plugins {
    alias(libs.plugins.momentum.application.library)
}

android {
    namespace = "ge.fitness.core.data"

}

dependencies {
    implementation(projects.core.domain)
}