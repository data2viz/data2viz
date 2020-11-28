plugins {
    id("io.data2viz.d2v.d2v-common")
    id("io.data2viz.d2v.d2v-js")
    id("io.data2viz.d2v.d2v-jvm")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":core"))
                api(project(":interpolate"))
                api(project(":time"))
            }
        }
    }
}
