import org.gradle.kotlin.dsl.android
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id("com.gradleup.gr8") version("0.11.2")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

val shadowedDependencies = configurations.create("shadowedDependencies")
//val compileOnlyDependencies: Configuration = configurations.create("compileOnlyDependencies")
//compileOnlyDependencies.extendsFrom(configurations.getByName("compileOnly"))

dependencies {
//    implementation(libs.kotlin.coroutines)
    add(shadowedDependencies.name, implementation("io.insert-koin:koin-core:3.5.6")!!)
    testImplementation(kotlin("test"))
}

gr8 {
    create("default") {
        // program jars are included in the final shadowed jar
        addProgramJarsFrom(shadowedDependencies)
        addProgramJarsFrom(tasks.getByName("assemble"))
        systemClassesToolchain {
            languageVersion.set(JavaLanguageVersion.of("11"))
        }
        // classpath jars are only used by R8 for analysis but are not included in the
        // final shadowed jar.
//        addClassPathJarsFrom(compileOnlyDependencies)
        proguardFile("rules.pro")

        // Use a version from https://storage.googleapis.com/r8-releases/raw
        // Requires a maven("https://storage.googleapis.com/r8-releases/raw") repository
        r8Version("8.8.19")
        // Or use a commit
        // The jar is downloaded on demand
        r8Version("887704078a06fc0090e7772c921a30602bf1a49f")
        // Or leave it to the default version
    }
}

