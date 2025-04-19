package ge.fitness.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.addComposeUiDependencies(project: Project){
    "implementation"(project(":core:presentation:ui"))
    "implementation"(project(":core:presentation:design-system"))

    "implementation"(project.libs.findBundle("compose").get())

}