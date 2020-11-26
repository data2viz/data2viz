import java.io.File
import java.util.*

object ProjectVersions {
//    const val androidBuildTools = "29.0.2"
//    const val androidSdk = 29
    const val version               = "0.8.9"
    const val bintray_version       = "1.8.4"
    const val coroutines_version    = "1.4.1"
    const val geojson_version       = "0.6.1-RC3"
    const val kotlin_version        = "1.4.20"
    const val sl4j_version          = "1.7.25"
    const val gradle_node_version   = "1.2.0"
    const val node_version          = "8.9.3"
    const val npm_version           = "5.5.1"
    const val stats_version         = "0.2.0"
    const val textfx_version        = "4.0.4-alpha"
}

object PlatformActivation {

    val include_android         : Boolean
    val include_js              : Boolean
    val include_jfx             : Boolean
    val include_jfx_renderings  : Boolean

    init {
        val props = Properties()
            .apply {
                load(File("gradle.properties").reader())
            }

        include_android         = props.getProperty("include_android").toBoolean()
        include_js              = props.getProperty("include_js").toBoolean()
        include_jfx             = props.getProperty("include_jfx").toBoolean()
        include_jfx_renderings  = props.getProperty("include_jfx_renderings").toBoolean()

        println( "include_android:: $include_android")
        println( "include_js:: $include_js")
        println( "include_jfx:: $include_jfx")
        println( "include_jfx_renderings:: $include_jfx_renderings")
    }

}

