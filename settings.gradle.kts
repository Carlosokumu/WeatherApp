
pluginManagement {
    repositories {
        jcenter()
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }

    plugins {
        id("com.android.application") version "4.1.0"
        id("org.jetbrains.kotlin.android") version "1.4.0"
        id("org.jetbrains.kotlin.android.extensions") version "1.4.0"


    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.application" -> useModule("com.android.tools.build:gradle:4.1.1")

            }
        }
    }
}

include("app")
rootProject.name = "TopUpMama"