pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }

    plugins {
        //  Hilt
        id("com.google.dagger.hilt.android") version "2.51.1" apply false
        id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DefLatam_ChatApp"
include(":app")