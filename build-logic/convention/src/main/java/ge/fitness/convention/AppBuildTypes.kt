package ge.fitness.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ApplicationExtensionType
) {
    commonExtension.run {

        buildFeatures {
            buildConfig = true
        }
        when (extensionType) {
            ApplicationExtensionType.ANDROID -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        release {
                            setUpReleaseType(commonExtension)
                        }
                        debug {
                            setUpDebugType()
                        }
                    }
                }
            }

            ApplicationExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        release {
                            setUpReleaseType(commonExtension)
                        }
                        debug {
                            setUpDebugType()
                        }
                    }
                }
            }
        }
    }
}


private fun BuildType.setUpReleaseType(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    buildConfigField("String", "BASE_URL", "\"run.mocky.io\"")
    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}

private fun BuildType.setUpDebugType() {
    buildConfigField("String", "BASE_URL", "\"run.mocky.io\"")
}


internal enum class ApplicationExtensionType {
    ANDROID,
    LIBRARY
}
