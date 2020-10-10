
job("Build and run core") {
    container("gradle:6.6-jdk8") {
        shellScript {
            content = "gradle :core:build -x test"
        }
    }
}
