
job("Build and test JFx") {
    container("gradle:6.6-jdk8") {
        shellScript {
            content = "gradle build -x jsBrowserTest -x jsTest -Pinclude_android=false -Pinclude_js=false -Pinclude_jfx=true --stacktrace"
        }
    }
}
