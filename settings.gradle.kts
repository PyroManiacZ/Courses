pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        kotlin("android") version "2.2.0"
        kotlin("jvm")     version "2.2.0"
        kotlin("plugin.serialization") version "2.2.0"
        kotlin("plugin.compose")       version "2.2.0"
        id("com.google.devtools.ksp")  version "2.2.0-2.0.2"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Courses"
include(":app", ":core", ":features")



