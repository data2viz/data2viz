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
                api(project(":shape"))
            }
        }
    }
}
