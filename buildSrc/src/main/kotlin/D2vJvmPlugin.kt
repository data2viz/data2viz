package io.data2viz.d2v

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

open class D2vJvmPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.configureKotlinJvm()
    }
}

private fun Project.configureKotlinJvm() = this.extensions.getByType<KotlinMultiplatformExtension>().run {
    if (project.hasJfx || project.hasAndroid) {
        jvm()
        this.sourceSets {

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


