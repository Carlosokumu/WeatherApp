
plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kapt)
    id("kotlin-android")
    id(BuildPlugins.objectBox)
    id(BuildPlugins.spek)

}


android {
    compileSdkVersion(AndroidSdk.compileSdkVersion)
    buildToolsVersion("30.0.2")

    android.buildFeatures.dataBinding = true

    defaultConfig {
        applicationId("com.example.topupmama")

        minSdkVersion(AndroidSdk.minSdkVersion)
        targetSdkVersion(AndroidSdk.targetSdkVersion)
        versionCode = AndroidSdk.versionCode
        versionName = AndroidSdk.versionName

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }



    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

//    testOptions {
//        junitPlatform {
//
//        }
//    }
    testOptions {
        junitPlatform.apply {
            filters {
                includeEngines("spek")
            }
        }
    }

    lintOptions.isAbortOnError = false
    lintOptions.isCheckReleaseBuilds = false
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
    

    implementation(Libraries.instaToast)
    implementation(Libraries.timber)


    //Blur Effect
    //implementation(Libraries.blurView)
    implementation(Libraries.realTm)
    implementation(Libraries.work)
    implementation(Libraries.swiperefreshlayout)
    //implementation(Libraries.moshi)


//    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
//    implementation 'androidx.core:core-ktx:1.7.0'
//    implementation 'androidx.appcompat:appcompat:1.4.1'
//    implementation 'com.google.android.material:material:1.5.0'
//    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
//    testImplementation 'junit:junit:4.+'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    testImplementation("org.junit.platform:junit-platform-engine:1.6.2")
    testImplementation("org.jetbrains.spek:spek-api:1.1.5")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.6")
    testImplementation("org.spekframework.spek2:spek-runner-junit5:2.0.6")
    testImplementation("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
    testImplementation("org.junit.platform:junit-platform-runner:1.4.0")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("io.mockk:mockk-agent-jvm:1.12.0") {
        because(
            "This dependency resolves the NoClassDefFoundError when using spek " +
                    "https://github.com/mockk/mockk/issues/605," +
                    "https://github.com/spekframework/spek/issues/968"
        )
    }
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    // test kotlinx.coroutines Flow
    testImplementation("app.cash.turbine:turbine:0.6.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("com.google.truth:truth:${Versions.truth}")
    testImplementation("com.google.truth:truth:${Versions.truth}")
    testImplementation("com.squareup.okhttp3:mockwebserver:5.0.0-alpha.2")
}