import ProjectVersions.coroutines_version

plugins {
    kotlin("multiplatform")
    id("io.data2viz.d2v.d2v-android")
    id("io.data2viz.d2v.d2v-js")
}

kotlin {

    d2vCommon(project)
    d2vJfx(project)

    sourceSets {
        if (project.hasJs) {
            val jsTest by getting {
                dependencies {
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutines_version")
                }
            }
        }

        if (project.hasJfx) {
            val jfxTest by getting {
                dependencies {
                    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                    api("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:$coroutines_version")
                }
            }
        }
    }

}
