// Top-level build file where you can add configuration options common to all sub-projects/modules.



plugins {

    id(BuildPlugins.androidApplication) apply false
    id(BuildPlugins.kotlinAndroid) apply false
    id(BuildPlugins.kotlinAndroidExtensions) apply false

}


allprojects {

    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
       // maven("http://dl.bintray.com/jetbrains/spek")
    }


}

buildscript {
    val kotlinVersion by extra("1.4.32")
    val kotlin_version by extra("1.4.32")
    val  objectboxVersion by extra("3.0.1")
    val junit5Version by extra("1.7.1.1")


    dependencies {

        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("io.objectbox:objectbox-gradle-plugin:$objectboxVersion")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:$junit5Version")

        //classpath("com.google.gms:google-services:4.3.8")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files

    }
}

