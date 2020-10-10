


job("Build and run core") {
    container("gradle") {
        // run 'gradle build'
        shellScript {
            content = "gradle build"
        }
    }
}
