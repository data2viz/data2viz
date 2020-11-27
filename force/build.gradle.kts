plugins {
    kotlin("multiplatform")
    id("io.data2viz.d2v.d2v-js")
}

kotlin {
    d2vCommon(project)
    d2vJvm(project)

    sourceSets {
        commonMain {
            dependencies {
                api(project(":core"))
                api(project(":quadtree"))
                api(project(":timer"))
            }
        }
    }
}
