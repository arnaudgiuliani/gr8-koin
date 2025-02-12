apply(plugin = "maven-publish")

val javadocJar = tasks.getByName("javadocJar")
val sourcesJar = tasks.getByName("sourcesJar")

configure<PublishingExtension> {
    publications {
        register<MavenPublication>("release") {
//            artifact(file("libs/android-library-0.14.0-Beta1-shaded.jar"))
            artifact(javadocJar)
            artifact(sourcesJar)
            // Include the JAR files in the publication
            afterEvaluate {
                from(components["release"])
            }
            pom {
                name.set("Kotzilla")
                description.set("Kotzilla")
                url.set("https://kotzilla.io/")
                licenses {
                    license {
                        name.set("Kotzilla SDK Licence 1.0")
                        url.set("https://doc.kotzilla.io/docs/about/licence")
                    }
                }
                scm {
                    url.set("https://github.com/kotzilla-io/kotzilla-sdk-sample")
                    connection.set("https://github.com/kotzilla-io/kotzilla-sdk-sample.git")
                }
                developers {
                    developer {
                        name.set("Kotzilla")
                        email.set("contact@kotzilla.io")
                    }
                }
            }
        }
    }
}


apply(from = file("../gradle/signing.gradle.kts"))