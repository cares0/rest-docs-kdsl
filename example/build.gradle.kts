import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.google.devtools.ksp")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.asciidoctor.jvm.convert")
    kotlin("jvm")
    kotlin("plugin.spring")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val snippetsDir = file("build/generated-snippets").also { extra["snippetsDir"] = it }
val asciidoctorExtensions: Configuration by configurations.creating

val querydslVersion = "5.0.0"
extra["springCloudVersion"] = "2022.0.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ksp"))
    ksp(project(":ksp"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctorExtensions("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.compileTestKotlin {
    dependsOn("kspTestKotlin")
    dependsOn("kspKotlin")
}

tasks.test {
    testLogging {
        showStandardStreams = true
        showCauses = true
        showExceptions = true
        showStackTraces = true
        exceptionFormat = TestExceptionFormat.FULL
    }
    outputs.dir(snippetsDir)
    reports.html.required = true
}

tasks.asciidoctor {
    configurations(asciidoctorExtensions.name)
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
    sources {
        include("**/index.adoc", "**/popup/*.adoc")
    }

    baseDirFollowsSourceFile()

    doFirst {
        delete(file("src/main/resources/static/docs"))
    }
}

tasks.register<Copy>("copyDocument").configure {
    dependsOn(tasks.asciidoctor)
    from(file("build/docs/asciidoc"))
    into(file("src/main/resources/static/docs"))
}

tasks.register<Copy>("buildDocument") {
    dependsOn("copyDocument")
    from(file("src/main/resources/static/docs"))
    into(file("build/resources/main/static/docs"))
}