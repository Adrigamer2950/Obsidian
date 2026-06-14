plugins {
    kotlin("jvm")
    id("io.papermc.paperweight.userdev")
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(21)
}

configurations.named("reobf") {
    attributes {
        attribute(
            TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE,
            17
        )
    }
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.REOBF_PRODUCTION