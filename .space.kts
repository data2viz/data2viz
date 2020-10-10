


job("Build and run core") {
    container("adoptopenjdk/openjdk8") {
        resources {
            memory = 768
        }

        kotlinScript { api ->
            // here goes complex logic
            api.gradlew("core:build")
        }
    }
}
