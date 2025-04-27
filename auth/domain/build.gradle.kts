plugins {
    alias(libs.plugins.momentum.library.jvm)

}

dependencies{
    implementation(projects.core.domain)
    implementation(libs.kotlinx.coroutines.core)
}