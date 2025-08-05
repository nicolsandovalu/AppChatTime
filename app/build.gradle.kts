plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.example.appchat"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.appchat"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // ===== TESTING (ahora usando el archivo libs.versions.toml) =====
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ===== KOTLIN COROUTINES (se mantiene la versión explícita) =====
    // Se recomienda mover estas dependencias a libs.versions.toml
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    // ===== LIFECYCLE (se mantiene la versión explícita) =====
    // Se recomienda mover estas dependencias a libs.versions.toml
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")

    // ===== HILT - INYECCIÓN DE DEPENDENCIAS (se mantiene la versión explícita) =====
    // Se recomienda mover estas dependencias a libs.versions.toml
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    // ===== ROOM - BASE DE DATOS LOCAL (se mantiene la versión explícita) =====
    // Se recomienda mover estas dependencias a libs.versions.toml
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // ===== NETWORKING (se mantiene la versión explícita) =====
    // Se recomienda mover estas dependencias a libs.versions.toml
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ===== UI COMPONENTS (se mantiene la versión explícita) =====
    // Se recomienda mover estas dependencias a libs.versions.toml
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")

    // ===== IMAGE LOADING (se mantiene la versión explícita) =====
    // Se recomienda mover estas dependencias a libs.versions.toml
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    // ===== JSON (se mantiene la versión explícita) =====
    // Se recomienda mover esta dependencia a libs.versions.toml
    implementation("com.google.code.gson:gson:2.10.1")

    // ===== FIREBASE =====

    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-inappmessaging-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // App Check (las versiones son gestionadas por el BoM)
    implementation("com.google.firebase:firebase-appcheck-playintegrity")
    implementation("com.google.firebase:firebase-appcheck-debug")
}
