package io.data2viz.d2v

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


val Project.hasJfx: Boolean
    get() = properties["include_jfx"] == "true"


open class D2vJfxPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("Using D2vJfxPlugin on project $project, is Jfx activated? ${ if (project.hasJfx) "yes" else "no"}")
        if (project.hasJfx.not())
            return
        project.configurePlugins()
        project.configureKotlinCommon()
    }
}

private fun Project.configurePlugins() {
//    plugins.apply("org.jetbrains.multiplatform")
}


private fun Project.configureKotlinCommon() = this.extensions.getByType<KotlinMultiplatformExtension>().run {
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

