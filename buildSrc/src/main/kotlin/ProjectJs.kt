import org.gradle.api.Project
import org.gradle.api.plugins.PluginAware
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType


fun KotlinMultiplatformExtension.d2vCommon(project: Project) {

    explicitApiWarning()
    sourceSets {
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                if (project.name != "tests") {
                    implementation(project(":tests"))
                }

            }
        }
    }


}

fun KotlinMultiplatformExtension.d2vJs(project: Project) {
    if (project.hasJs) {
        js(KotlinJsCompilerType.BOTH) {
            browser {
            }
        }
        sourceSets {
            val jsTest by getting {
                dependencies {
                    implementation(kotlin("test-js"))
                }
            }
        }
    }
}

fun KotlinMultiplatformExtension.d2vJvm(project: Project) {
    if (project.hasJfx || project.hasAndroid) {
        jvm()
        sourceSets {

            val jvmMain by getting {
            }
            val jvmTest by getting {
                dependencies {
                    implementation(kotlin("test-junit"))
                }
            }

        }
    }
}

fun KotlinMultiplatformExtension.d2vJfx(project: Project) {
    if (project.hasJfx) {
        jvm("jfx") {}
        sourceSets {

            val jfxMain by getting {
            }
            val jfxTest by getting {
                dependencies {
                    implementation(kotlin("test-junit"))
                }
            }

        }
    }
}

//fun PluginAware.d2vAndroid() {
//    if (project.hasAndroid) {
//        apply(plugin = "com.android.library")
//        configure<LibraryExtension>() {
//            compileSdkVersion(28)
//            defaultConfig {
//                minSdkVersion(17)
//                targetSdkVersion(28)
//                versionCode = 1
//                versionName = "$version"
//                testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
//            }
//            buildTypes {
//                getByName("release") {
//                    isMinifyEnabled = false
//                }
//            }
//        }
//    }
//}



//fun d2vAndroid(project: Project) {
//    if (project.hasAndroid) {
//        apply(plugin = "com.android.library")
//        configure<com.android.build.gradle.LibraryExtension>() {
//            compileSdkVersion(28)
//            defaultConfig {
//                minSdkVersion(17)
//                targetSdkVersion(28)
//                versionCode = 1
//                versionName = "$version"
//                testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
//            }
//            buildTypes {
//                getByName("release") {
//                    isMinifyEnabled = false
//                }
//            }
//        }
//    }
//}

fun KotlinMultiplatformExtension.d2vAndroid(project: Project) {

    if (project.hasAndroid) {
//
//        apply(plugin = "android")

        android {

        }
        jvm("jfx") {}
        sourceSets {

            val jvmMain by getting {
            }
            val jvmTest by getting {
                dependencies {
                    implementation(kotlin("test-junit"))
                }
            }

        }
    }

//    if (include_android.toBoolean()) {
//        apply plugin: 'com.android.library'
//        android {
//            compileSdkVersion 28
//            defaultConfig {
//                minSdkVersion 17
//                targetSdkVersion 28
//                versionCode 1
//                versionName "$version"
//                testInstrumentationRunner 'android.support.test.runner.AndroidJUnitRunner'
//            }
//            buildTypes {
//                release {
//                    minifyEnabled false
//                }
//            }
//        }
//
//        kotlin {
//            explicitApiWarning()
//
//            android() {
//                publishLibraryVariants("debug", "release")
//            }
//            sourceSets {
//                main {
//                    dependencies {
//                        implementation kotlin('stdlib')
//                    }
//                }
//                test {
//                    dependencies {
//                        implementation kotlin('stdlib')
//                        implementation project(":tests")
//                        implementation kotlin('test')
//                        implementation kotlin('test-junit')
//                    }
//                }
//            }
//        }
//
//    }

}



