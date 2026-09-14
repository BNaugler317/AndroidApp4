plugins {
    alias(libs.plugins.android.application)
    //alias(libs.plugins.kotlin.android)
    //id("org.jetbrains.kotlin.kapt")
    // alias(libs.plugin.kotlin.android)
    // alias(libs.plugins.ksp)
    // id("com.android.application")
    // alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.androidapp4"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.androidapp4"
        minSdk = 37
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Glide
    implementation(libs.glide)
    //kapt(libs.glide.compiler)
    ksp(libs.glide.ksp)

    // Gson
    implementation(libs.gson)

    //RecyclerView
    implementation(libs.recyclerview)

    // Lifecycle ViewModel
    implementation(libs.lifecycle.viewmodel)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}