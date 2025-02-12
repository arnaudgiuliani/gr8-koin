import org.gradle.kotlin.dsl.libs

plugins {
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
}

buildscript {
    repositories {
        mavenCentral()
    }
}

allprojects {

    group = "io.kotzilla"
    version = "1.0"
}