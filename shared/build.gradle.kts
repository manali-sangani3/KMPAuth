plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)

    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("native.cocoapods")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    cocoapods {
        summary = "Shared module"
        homepage = "https://example.com"
        version = "1.0.0"

        ios.deploymentTarget = "14.1"

        framework {
            baseName = "shared"
            isStatic = true
        }
    }
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.framework {
            baseName = "shared"
        }
    }
    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(libs.compose.ui)
                implementation(compose.material3)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:2.8.0")

                implementation(libs.ktor.core)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.logging)

                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(compose.materialIconsExtended)
                implementation(compose.components.resources)
                implementation(libs.kamel.image)
                implementation(libs.settings)
                implementation(libs.settings.no.arg)

            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.ktor.android)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.ktor.serialization)

            }
        }
        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        iosX64()
        iosArm64()
        iosSimulatorArm64()
        val iosX64Main by getting { dependsOn(iosMain) }
        val iosArm64Main by getting { dependsOn(iosMain) }
        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }


    }
}

android {
    namespace = "com.example.kmpauth"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}
dependencies {
    implementation(libs.transport.runtime)
}

