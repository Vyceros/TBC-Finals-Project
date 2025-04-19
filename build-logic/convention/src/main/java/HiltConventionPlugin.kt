import ge.fitness.convention.ModuleType
import ge.fitness.convention.configureHilt
import org.gradle.api.Plugin
import org.gradle.api.Project

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val moduleType = when {
                pluginManager.hasPlugin("com.android.library") ||
                        pluginManager.hasPlugin("com.android.application") -> ModuleType.ANDROID

                pluginManager.hasPlugin("org.jetbrains.kotlin.jvm") -> ModuleType.JVM
                else -> {
                    ModuleType.ANDROID
                }
            }
            configureHilt(moduleType)
        }
    }
}