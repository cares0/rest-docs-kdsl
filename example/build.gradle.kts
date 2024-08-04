import org.gradle.api.tasks.testing.logging.TestExceptionFormat

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

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val snippetsDir = file("build/generated-snippets").also { extra["snippetsDir"] = it }
val asciidoctorExtensions: Configuration by configurations.creating

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

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
    test {
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
    asciidoctor {
        configurations(asciidoctorExtensions.name)
        inputs.dir(snippetsDir)
        dependsOn(test)
        sources {
            include("**/index.adoc", "**/popup/*.adoc")
        }

        baseDirFollowsSourceFile()

        doFirst {
            delete(file("src/main/resources/static/docs"))
        }
    }
    register<Copy>("copyDocument") {
        dependsOn(asciidoctor)
        from(file("build/docs/asciidoc"))
        into(file("src/main/resources/static/docs"))
    }
    register<Copy>("buildDocument") {
        dependsOn("copyDocument")
        from(file("src/main/resources/static/docs"))
        into(file("build/resources/main/static/docs"))
    }
    bootJar { dependsOn("buildDocument") }
    jar { dependsOn("buildDocument") }
    resolveMainClassName { dependsOn("buildDocument") }
}