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
        println("Using D2vJvmPlugin on project $project, is Jvm activated? ${ if (project.hasJs) "yes" else "no"}")
        if (project.hasJs.not())
            return
        project.configurePlugins()
        project.configureKotlinJs()
    }
}

private fun Project.configurePlugins() {
//    plugins.apply("org.jetbrains.multiplatform")
}


private fun Project.configureKotlinJs() = this.extensions.getByType<KotlinMultiplatformExtension>().run {
    js {
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



//private fun Project.publications() {
//    extensions
//        .getByType<PublishingExtension>()
//        .setupPublishRepo(this)
//
//}
