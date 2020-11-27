package io.data2viz.d2v

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

val Project.hasJs: Boolean
    get() = properties["include_js"] == "true"


open class D2vJsPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("Using D2vJsPlugin on project $project, is Js activated? ${if (project.hasJs) "yes" else "no"}")
        if (project.hasJs.not())
            return

        project.configureKotlinJs()
        project.addTaskPrepareNpm()
        project.addTaskPublishNpm()

    }
}


lateinit var jsOutputs: FileCollection

private fun Project.configureKotlinJs() = this.extensions.getByType<KotlinMultiplatformExtension>().run {
//    targets.jvm.compilations.all

    js {
        browser {
        }
        jsOutputs = compilations["main"].output.allOutputs

        //    from(kotlin.targets.js.compilations.main.output.allOutputs)
    }
    sourceSets {
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }


}


private fun Project.addTaskPrepareNpm() {
    val compileJs = tasks.named<Kotlin2JsCompile>("compileKotlinJs")
    println("compileJs:: $compileJs")
    tasks.create<Copy>("preparePublishNpm") {
        dependsOn(compileJs)
        println("preparePublishNpm")
        from(jsOutputs)
        from("../build/js/packages/d2v-${project.name}/package.json")
        into("build/npm")

        doLast {
            //Replace package json by a brand new one, just keeping d2v dependencies
            val jsonFile = file("$buildDir/npm/package.json")
            val parsedJson = groovy.json.JsonSlurper().parseText(jsonFile.readText())
            val gDeps = ((parsedJson as Map<String, Any>).get("dependencies")) as Map<String, Any>
            val deps = gDeps.entries.map {
                Pair(it.key, it.toString())
            }.toMap()

            println(deps)
            jsonFile.writeText(
                generatePackageJson(project.version.toString(), project.name, deps)
            )
        }
    }
}

fun generatePackageJson(version: String, projectName: String, dependencies: Map<String, String>): String {
    val d2vDeps = dependencies.filter { it.key.contains("d2v") }
    var d2vDependencies = if (d2vDeps.isEmpty()) "" else

        d2vDeps.entries.joinToString(
            prefix = """"dependencies" : { """,
            transform = { """  "@data2viz/${it.key}": "$version"  """ },
            postfix = " },"
        )


    return """
{
    "name": "@data2viz/d2v-$projectName",
    "version" : "$version",
    "description" : "Data2viz $projectName module",
    "author": "Data2viz",
    "main": "d2v-${projectName}.js",
    "license": "Apache-2.0",
    "homepage": "https://data2viz.io",
    $d2vDependencies
    "bugs": {"url": "https://github.com/data2viz/data2viz/issues"},
    "repository": {
        "type": "git",
        "url": "git+https://github.com/data2viz/data2viz.git"
    },
    "keywords": [
        "Kotlin",
        "JavaScript",
        "data2viz"
    ]
}
""".toString()
}

private fun Project.addTaskPublishNpm() {
    val preparePublishNpm = tasks.named("preparePublishNpm")
    tasks.create<Exec>("publishNpm") {
        dependsOn(preparePublishNpm)

        check(project.hasProperty("npmToken")) { "To publish on npm the build needs a npmToken property." }

        val npmToken = project.property("npmToken").toString()

        workingDir = file("build/npm")
//        commandLine = listOf("npm", "publish", "--dry-run", "--//registry.npmjs.org/:_authToken=$npmToken")
        commandLine = listOf("npm", "publish", "--access=public", "--//registry.npmjs.org/:_authToken=$npmToken")
    }

}

