plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "com.stochastictinkr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(libs.jSystemThemeDetector)
    implementation(libs.flatLaf)
    implementation(libs.kotlin.reflect)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}