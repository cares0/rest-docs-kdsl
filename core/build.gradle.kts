val kspVersion: String by project

plugins {
    kotlin("jvm")
}

group = "cares.restdocskdsl"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    api("org.springframework.restdocs:spring-restdocs-core:3.0.1")
    api("org.springframework.restdocs:spring-restdocs-mockmvc:3.0.1")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}