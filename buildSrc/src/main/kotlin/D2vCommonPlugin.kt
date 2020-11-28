package io.data2viz.d2v

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension



open class D2vCommonPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.plugins.apply("org.jetbrains.kotlin.multiplatform")
        project.configureKotlinCommon()
    }
}

private fun Project.configureKotlinCommon() = this.extensions.getByType<KotlinMultiplatformExtension>().run {
    explicitApiWarning()
    sourceSets {
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
        }

        getByName("commonTest") {
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

