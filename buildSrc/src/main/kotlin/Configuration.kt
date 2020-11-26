import org.gradle.api.Project


val Project.hasJs: Boolean
    get() = properties["include_js"] == "true"

val Project.hasAndroid: Boolean
    get() = properties["include_android"] == "true"

val Project.hasJfx: Boolean
    get() = properties["include_jfx"] == "true"


