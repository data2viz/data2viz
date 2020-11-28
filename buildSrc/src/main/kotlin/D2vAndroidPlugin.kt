package io.data2viz.d2v

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

val Project.hasAndroid: Boolean
    get() = properties["include_android"] == "true"


private typealias AndroidBaseExtension = BaseExtension

open class D2vAndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("Using D2vAndroidPlugin on project $project, is android activated? ${ if (project.hasAndroid) "yes" else "no"}")
        if (project.hasAndroid.not())
            return

        project.plugins.apply("com.android.library")
        project.plugins.apply("org.jetbrains.kotlin.multiplatform")

        project.configureAndroid()
        project.configureKotlin()
    }
}

private fun Project.configureAndroid() = this.extensions.getByType<AndroidBaseExtension>().run {
    compileSdkVersion(28)
    defaultConfig {
        minSdkVersion(17)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
        getByName("debug") {
            isTestCoverageEnabled = true
        }
    }

    sourceSets.getByName("main") {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDir("src/androidMain/res")
    }

    packagingOptions {
        exclude("META-INF/NOTICE.txt")
        // ...
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


private fun Project.configureKotlin() = this.extensions.getByType<KotlinMultiplatformExtension>().run {
    android {
        publishLibraryVariants("debug", "release")
    }
    sourceSets {
        getByName("androidMain") {
            dependencies {
            }
        }

        getByName("androidTest") {
            dependencies {
                implementation(project(":tests"))
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
    }
}
