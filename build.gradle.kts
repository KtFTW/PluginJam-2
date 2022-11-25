import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val javaVersion = 17

plugins {
    kotlin("jvm") version "1.7.21"
    id("io.papermc.paperweight.userdev") version "1.3.8"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("de.nycode.spigot-dependency-loader") version "1.1.2"
}

group = "net.stckoverflw"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")

    // KSpigot dependency
    spigot("net.axay", "kspigot", "1.19.0")
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Xjdk-release=$javaVersion",
            )
            jvmTarget = "$javaVersion"
        }
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(javaVersion)
    }
    assemble {
        dependsOn(reobfJar)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}