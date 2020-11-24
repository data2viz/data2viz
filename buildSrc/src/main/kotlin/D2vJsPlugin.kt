package io.data2viz.d2v

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.getByType

open class D2vJsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("Using D2vJsPlugin on project $project")
    }
}




//private fun Project.publications() {
//    extensions
//        .getByType<PublishingExtension>()
//        .setupPublishRepo(this)
//
//}
