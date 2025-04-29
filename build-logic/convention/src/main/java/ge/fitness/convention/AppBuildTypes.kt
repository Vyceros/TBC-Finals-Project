package ge.fitness.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
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
        val apiKey = gradleLocalProperties(rootDir, rootProject.providers).getProperty("API_KEY")
        val backupKey =
            gradleLocalProperties(rootDir, rootProject.providers).getProperty("BACKUP_KEY")
        when (extensionType) {
            ApplicationExtensionType.ANDROID -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        release {
                            setUpReleaseType(commonExtension, apiKey, backupKey)
                        }
                        debug {
                            setUpDebugType(apiKey, backupKey)
                        }
                    }
                }
            }

            ApplicationExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        release {
                            setUpReleaseType(commonExtension, apiKey, backupKey)
                        }
                        debug {
                            setUpDebugType(apiKey, backupKey)
                        }
                    }
                }
            }
        }
    }
}


private fun BuildType.setUpReleaseType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String,
    backupKey: String,
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BACKUP_KEY", "\"$backupKey\"")
    buildConfigField("String", "BASE_URL", "\"https://wger.de/api/v2/\"")
    buildConfigField("String", "EXERCISE_URL", "\"https://exercisedb.p.rapidapi.com/\"")
    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}

private fun BuildType.setUpDebugType(
    apiKey: String,
    backupKey: String
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BACKUP_KEY", "\"$backupKey\"")
    buildConfigField("String", "BASE_URL", "\"https://wger.de/api/v2/\"")
    buildConfigField("String", "EXERCISE_URL", "\"https://exercisedb.p.rapidapi.com/\"")
}


internal enum class ApplicationExtensionType {
    ANDROID,
    LIBRARY
}
