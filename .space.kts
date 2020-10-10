
job("Build and run core") {
    container("gradle:6.6-jdk8") {
        kotlinScript { api ->
            api.gradle(":core:build", "-x", "test")
        }
    }
}
