package ge.fitness.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


internal fun Project.configureHilt(
    moduleType: ModuleType
){
    pluginManager.apply("com.google.devtools.ksp")

    dependencies{
        "ksp"(libs.findLibrary("hilt.compiler").get())
    }
    when(moduleType){
        ModuleType.ANDROID -> {
            pluginManager.apply("dagger.hilt.android.plugin")

            dependencies {
                "implementation"(libs.findLibrary("hilt.android").get())
                "implementation"(libs.findLibrary("androidx-hilt-navigation-compose").get())
            }

        }
        ModuleType.JVM -> {
            dependencies{
                "implementation"(libs.findLibrary("hilt.core").get())
            }
        }
    }
}

internal enum class ModuleType {
    ANDROID,
    JVM
}