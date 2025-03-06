import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.android
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id("com.gradleup.shadow") version "8.3.5"
}

val androidCompileSDK : String by project
val androidMinSDK : String by project

android {
    namespace = "io.kotzilla.sample.sdk"
    compileSdk = androidCompileSDK.toInt()
    defaultConfig {
        minSdk = androidMinSDK.toInt()
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        val debug by getting {
        }
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

tasks.withType<KotlinCompile>().all {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

dependencies {

//    implementation(libs.kotlin.coroutines)
//    implementation(libs.koin.core)

//    shadow(libs.kotlin.coroutines)
//    implementation(libs.koin.core)

//    implementation(libs.kotlin.coroutines)

//    implementation(libs.koin.core)
    implementation(files("libs/android-library-1.0-shaded.jar"))
}

// Register the shadowJar task using the release runtime classpath.
tasks.register<ShadowJar>("shadowJar") {
    archiveClassifier.set("shaded") // This will produce "android-library-shaded.jar"
    configurations = listOf(project.configurations.getByName("releaseCompileClasspath"))

    exclude("**/kotlin/**")
    relocate("org.koin", "io.kotzilla.koin")
}

    // Exclude duplicate resources
