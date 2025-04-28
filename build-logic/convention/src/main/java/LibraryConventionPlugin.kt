import com.android.build.api.dsl.LibraryExtension
import ge.fitness.convention.ApplicationExtensionType
import ge.fitness.convention.configureBuildTypes
import ge.fitness.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class LibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
                apply("com.android.library")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ApplicationExtensionType.LIBRARY
                )

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }
            dependencies {
                "testImplementation"(kotlin("test"))
            }
        }
    }
}