plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ahmad.commons"
    compileSdk = 34

    defaultConfig {
        minSdk = 22
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures{
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    //    androidTestImplementation(libs.androidx.espresso.core)
    api("com.tencent:mmkv:1.3.9")
    api("com.github.bumptech.glide:glide:4.16.0")
    api("com.intuit.sdp:sdp-android:1.0.6")
    api ("com.squareup.retrofit2:retrofit:2.11.0")
    api ("com.squareup.retrofit2:converter-gson:2.9.0")
    api ("com.squareup.retrofit2:converter-scalars:2.9.0")
}