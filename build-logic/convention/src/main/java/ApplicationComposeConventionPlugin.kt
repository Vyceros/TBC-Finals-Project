
import com.android.build.api.dsl.ApplicationExtension
import ge.fitness.convention.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class ApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("momentum.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByType<ApplicationExtension>()
            configureCompose(extension)
        }
    }
}