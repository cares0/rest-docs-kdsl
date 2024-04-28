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
    implementation(kotlin("reflect"))
    api(project(":core"))
    implementation("com.squareup:kotlinpoet-ksp:1.18.0")
    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
}