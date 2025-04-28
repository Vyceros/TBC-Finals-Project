
import com.android.build.api.dsl.ApplicationExtension
import ge.fitness.convention.ApplicationExtensionType
import ge.fitness.convention.configureBuildTypes
import ge.fitness.convention.configureKotlinAndroid
import ge.fitness.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.gms.google-services")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("applicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdk").get().toString().toInt()

                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ApplicationExtensionType.ANDROID
                )

                configureKotlinAndroid(this)
            }
        }
    }
}
