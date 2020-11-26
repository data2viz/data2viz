import com.android.build.gradle.LibraryExtension

plugins {
    kotlin("multiplatform")
}



kotlin {
    d2vCommon(project)
    d2vJs(project)
    d2vJvm(project)

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

