plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    mavenCentral()
    google()
    jcenter()
    maven("https://maven.pkg.jetbrains.space/kotlin/p/dokka/dev")
}


dependencies {
    compileOnly(gradleKotlinDsl())
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.4.20")
}



afterEvaluate {



    println("After evaluate buildSrc")

//    publishing {
//        publications {
//            all { publication ->
//                println("Publication:: ${publication.name}")
//                publication.artifacts.with { artifacts ->
//
//                    def sourceArtifact
//                    artifacts.each {
//                        if (it.file.name.contains("sources")) {
//                            println "removing source artifact :: $it.file.name"
//                            sourceArtifact = it
//                        }
//                    }
//                    remove(sourceArtifact)
//                }
//            }
//        }
//    }
}

