plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }
    
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    
    js {
        browser()
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kermit)
            implementation(projects.shared) // Add dependency on shared module to access VideoPlatformService
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.kermit.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
//            implementation(libs.kermit.iosx64)
//            implementation(libs.kermit.iosarm64)
//            implementation(libs.kermit.iossimulatorarm64)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.java)
            implementation(libs.kermit.jvm)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(libs.kermit.js)
        }
    }
}

android {
    namespace = "dev.waylon.player.apis"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}