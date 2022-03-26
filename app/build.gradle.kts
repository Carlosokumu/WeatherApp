
plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kapt)
    id("kotlin-android")
    id(BuildPlugins.objectBox)

}


android {
    compileSdkVersion(AndroidSdk.compileSdkVersion)
    buildToolsVersion("30.0.2")

    android.buildFeatures.dataBinding = true

    defaultConfig {
        applicationId ("com.example.topupmama")

        minSdkVersion(AndroidSdk.minSdkVersion)
        targetSdkVersion(AndroidSdk.targetSdkVersion)
        versionCode =  AndroidSdk.versionCode
        versionName = AndroidSdk.versionName

        testInstrumentationRunner ("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {


    implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation (Libraries.constraintLayout)
   // implementation(Libraries.appCompat)
     implementation (Libraries.material)



    implementation(Libraries.room)
    implementation(Libraries.roomRuntime)

    // implementation("androidx.appcompat:appcompat:1.4.1")
   // implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
   // implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    kapt(Libraries.roomCompiler)


    implementation(Libraries.retrofit)
    implementation(Libraries.gson)
    implementation(Libraries.okhttp)
    implementation(Libraries.loggingInterceptor)



    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)

    implementation(Libraries.coroutines)
    implementation(Libraries.coroutinesAndroid)

    // Lifecycle
    implementation(Libraries.viewModel)
    implementation(Libraries.livedata)
    implementation(Libraries.lifecycle)
    implementation(Libraries.viewModelSavedState)


    //Blur Effect
    implementation(Libraries.blurView)
    implementation(Libraries.realTm)
    implementation(Libraries.work)
    implementation(Libraries.swiperefreshlayout)


//    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
//    implementation 'androidx.core:core-ktx:1.7.0'
//    implementation 'androidx.appcompat:appcompat:1.4.1'
//    implementation 'com.google.android.material:material:1.5.0'
//    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
//    testImplementation 'junit:junit:4.+'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}