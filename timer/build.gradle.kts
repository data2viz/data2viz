import ProjectVersions.coroutines_version

plugins {
    kotlin("multiplatform")
}

kotlin {

    d2vCommon(project)
    d2vJs(project)
    d2vJfx(project)

    sourceSets {
        val jsTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutines_version")
            }
        }
        val jfxTest by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:$coroutines_version")
            }
        }
    }

}
