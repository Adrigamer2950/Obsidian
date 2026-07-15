pluginManagement {
    plugins {
        kotlin("jvm") version "2.4.10"
        id("io.papermc.paperweight.userdev") version "2.0.0-SNAPSHOT"
    }
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "Obsidian"

include("core", "folia", "plugin", "nms")
