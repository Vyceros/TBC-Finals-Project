import ge.fitness.convention.addComposeUiDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("momentum.android.library.compose")
                apply("momentum.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                addComposeUiDependencies(target)
            }
        }
    }
}