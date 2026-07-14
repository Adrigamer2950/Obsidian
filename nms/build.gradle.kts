plugins {
    kotlin("jvm")
    id("java")
    //id("io.papermc.paperweight.userdev")
}

dependencies {
    compileOnly(libs.paper.api)
    //paperweight.paperDevBundle("1.17.1-R0.1-SNAPSHOT")
    compileOnly("xyz.jpenilla:reflection-remapper:0.1.3")
}

val targetJavaVersion = (rootProject.properties["java-version"] as String).toInt()

kotlin {
    jvmToolchain(targetJavaVersion)
}

//paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.REOBF_PRODUCTION
