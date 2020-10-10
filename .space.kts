
job("Build and run core") {
    container("gradle:6.6-jdk8") {
        shellScript {
            content = "gradle build -x jsTest -x jsBrowserTest -x jvmTest  -Pinclude_android=false -Pinclude_js=true -Pinclude_jfx=false --stacktrace"
        }
    }
}
