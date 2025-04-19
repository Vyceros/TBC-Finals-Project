plugins {
    `kotlin-dsl`
}
group = "ge.fitness.momentum.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "momentum.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("composeApplication") {
            id = "momentum.android.compose"
            implementationClass = "ApplicationComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "momentum.android.library"
            implementationClass = "LibraryConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "momentum.android.library.compose"
            implementationClass = "LibraryComposeConventionPlugin"
        }

        register("androidFeaturePlugin") {
            id = "momentum.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("hiltPlugin") {
            id = "momentum.hilt"
            implementationClass = "HiltConventionPlugin"
        }
    }
}

