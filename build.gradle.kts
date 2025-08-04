// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
    }
}

plugins {
    // Aplica el plugin de la aplicación de Android.
    alias(libs.plugins.android.application) apply false
    // Aplica el plugin de Kotlin para Android.
    alias(libs.plugins.kotlin.android) apply false
    // Aplica el plugin de KSP (Kotlin Symbol Processing) para Room y Hilt.
    id("com.google.devtools.ksp") version "2.2.0-2.0.2" apply false
    // Aplica el plugin de Hilt para la inyección de dependencias.
    id("com.google.dagger.hilt.android") version "2.57" apply false
    // Aplica el plugin de Google Services para Firebase.
    id("com.google.gms.google-services") version "4.4.3" apply false
    // Añade el plugin de Firebase Crashlytics.
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
}