plugins {
    id("io.data2viz.d2v.d2v-common")
    id("io.data2viz.d2v.d2v-android")
    id("io.data2viz.d2v.d2v-jfx")
    id("io.data2viz.d2v.d2v-js")
}

kotlin {

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
