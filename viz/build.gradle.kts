plugins {
    kotlin("multiplatform")
    id("io.data2viz.d2v.d2v-js")
}

kotlin {
    d2vCommon(project)
    d2vJfx(project)

    sourceSets {
        commonMain {
            dependencies {
                api(project(":color"))
                api(project(":core"))
                api(project(":timer"))
            }
        }

        if (project.hasJfx) {
            val jfxTest by getting {
                dependencies {
                    api("org.testfx:testfx-core:${ProjectVersions.textfx_version}")
                    api("org.testfx:testfx-junit:${ProjectVersions.textfx_version}")
                }
            }
        }

    }
}


