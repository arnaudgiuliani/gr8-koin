import org.gradle.kotlin.dsl.libs

plugins {
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.dokka).apply(false)
//    `kotlin-dsl`.apply(false)
//    alias(libs.plugins.kotlinBinary)
    alias(libs.plugins.nexusPublish)
}

//nexusPublishing {
//    repositories {
//        sonatype {
//            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
//            username.set("")
//            password.set("")
//        }
//    }
//}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.github.kezong:fat-aar:1.3.8")
    }
}

allprojects {

    val kotzillaVersion: String by project

    group = "io.kotzilla"
    version = kotzillaVersion

    apply(plugin = "org.jetbrains.dokka")
    val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)
    val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
        from(dokkaHtml.outputDirectory)
    }
}