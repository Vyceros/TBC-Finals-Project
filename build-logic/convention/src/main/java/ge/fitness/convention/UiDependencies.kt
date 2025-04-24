package ge.fitness.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.addComposeUiDependencies(project: Project){
    "implementation"(project(":core:presentation:ui"))
    "implementation"(project(":core:presentation:design-system"))

    "implementation"(project.libs.findBundle("compose").get())
    "implementation"(project.libs.findLibrary("kotlinx.serialization.json").get())
    "implementation"(project.libs.findLibrary("androidx.hilt.navigation.compose").get())
    "implementation"(project.libs.findLibrary("androidx.navigation.compose").get())

}