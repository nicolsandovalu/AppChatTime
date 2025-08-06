pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }

    plugins {
        // Agrega esto para Hilt
        id("com.google.dagger.hilt.android") version "2.51.1" apply false
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DefLatam_ChatApp"
include(":app")}