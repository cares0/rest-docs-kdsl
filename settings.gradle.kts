pluginManagement {
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
        id("com.google.devtools.ksp") version kspVersion
        id("org.springframework.boot") version "3.1.1"
        id("io.spring.dependency-management") version "1.1.2"
        id("org.asciidoctor.jvm.convert") version "3.3.2"
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}


rootProject.name = "rest-docs-kdsl"
include("core")
include("example")
include("ksp")
include("ksp")
