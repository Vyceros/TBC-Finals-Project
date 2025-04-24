plugins {
    alias(libs.plugins.momentum.library.jvm)

}

dependencies{
    implementation("javax.inject:javax.inject:1")
    implementation(projects.core.domain)
}