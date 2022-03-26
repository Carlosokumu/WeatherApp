object Versions {


    const val material  = "1.2.0-alpha06"
    const val constraintLayout = "2.0.4"
    const val buildToolsVersion = "4.1.0"
    const val kotlin = "1.4.32"

    const val room = "2.3.0-alpha02"


    const val retrofit = "2.9.0"
    const val okhttp = "4.8.1"
    const val loggingInterceptor = "4.8.1"


    const val coroutines = "1.3.9"

    const val koin = "2.1.1"



    const val lifecycle = "2.3.0-alpha07"
    const val blurView = "1.0.2"
    const val realTime = "1.2.1"
    const val workVersion = "2.1.0"
    const val swiperefreshlayout = "1.2.0-alpha01"



}


object BuildPlugins {
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val androidApplication = "com.android.application"
    const val kapt = "kotlin-kapt"
    const val kotlinAndroidExtensions = "org.jetbrains.kotlin.android.extensions"
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val objectBox = "io.objectbox"
}


object Libraries {


    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val room = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"


    // Coroutines
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    //Koin

    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    // Lifecycle
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val viewModelSavedState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
   

    const val blurView = "com.eightbitlab:supportrenderscriptblur:${Versions.blurView}"
    const val realTm = "com.github.mmin18:realtimeblurview:${Versions.realTime}"
    const val work = "androidx.work:work-runtime-ktx:${Versions.workVersion}"
    const val swiperefreshlayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"

}

object AndroidSdk {
    const val minSdkVersion = 22
    const val compileSdkVersion = 30
    const val targetSdkVersion = compileSdkVersion
    const val versionCode = 1
    const val versionName = "1.0"
}



