package io.data2viz.d2v

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.getByType

val Project.hasAndroid: Boolean
    get() = properties["include_android"] == "true"


private typealias AndroidBaseExtension = BaseExtension

open class D2vAndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("Using D2vAndroidPlugin on project $project, is android activated? ${ if (project.hasAndroid) "yes" else "no"}")
        if (project.hasAndroid.not())
            return
        project.configurePlugins()
        project.configureAndroid()
    }
}

private fun Project.configurePlugins() {
    plugins.apply("com.android.library")
}


private fun Project.configureAndroid() = this.extensions.getByType<AndroidBaseExtension>().run {
    compileSdkVersion(28)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 2
        versionName = "1.0.1"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isTestCoverageEnabled = true
        }
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