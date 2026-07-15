plugins {
    kotlin("jvm")
    id("java")
    //id("io.papermc.paperweight.userdev")
}

dependencies {
    compileOnly(libs.reflections)
    compileOnly(libs.paper.api)
    //paperweight.paperDevBundle("1.17.1-R0.1-SNAPSHOT")
    implementation(libs.reflection.remapper)
}

val targetJavaVersion = (rootProject.properties["java-version"] as String).toInt()

kotlin {
    jvmToolchain(targetJavaVersion)
}

//paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.REOBF_PRODUCTION
