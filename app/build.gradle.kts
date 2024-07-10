plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "1.9.23"
    id("com.google.devtools.ksp") version "2.0.0-1.0.22"
    id("androidx.room")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "rs.edu.raf.cataquiz"
    compileSdk = 34

    defaultConfig {
        applicationId = "rs.edu.raf.cataquiz"
        minSdk = 30
        // AGP 8.5 doesn't support targeting API 35 yet.
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Icons
    implementation(libs.androidx.material.icons.extended)

    // Camera
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)

    // Jetpack Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // Hilt Compose Navigation support (hiltViewModel factory)
    implementation(libs.androidx.hilt.navigation.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    testImplementation(libs.mockwebserver)

    // KotlinX Serialization
    implementation(libs.kotlinx.serialization.json)

    // Coil
    implementation(libs.coil.compose)

    // DataStore
    implementation(libs.androidx.datastore)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)


    /*
     * Testing
     */
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    testImplementation(libs.kotest.assertions.core)

    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)

    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(libs.core.ktx)
    testImplementation(libs.androidx.junit.ktx)
}