import com.android.build.gradle.LibraryExtension

plugins {
    id("io.data2viz.d2v.d2v-android")
    id("io.data2viz.d2v.d2v-common")
    id("io.data2viz.d2v.d2v-jfx")
    id("io.data2viz.d2v.d2v-js")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":core"))
                api(project(":scale"))
                api(project(":viz"))
            }
        }

        commonTest {
            dependencies {
                api(project(":format"))
            }

        }
    }
}

