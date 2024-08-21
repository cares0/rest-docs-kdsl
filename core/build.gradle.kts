import com.vanniktech.maven.publish.SonatypeHost

val kspVersion: String by project

plugins {
    kotlin("jvm")
    id("com.vanniktech.maven.publish") version "0.29.0"
}

repositories {
    mavenCentral()
}

mavenPublishing {
    coordinates(
        groupId = "io.github.cares0",
        artifactId = "rest-docs-kdsl-core",
        version = "1.0.4"
    )

    pom {
        name.set("rest-docs-kdsl")
        description.set("Simplify Spring REST Docs with Kotlin DSL")
        inceptionYear.set("2024")
        url.set("https://github.com/cares0/rest-docs-kdsl")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("cares0")
                name.set("YoungJun Kim")
                email.set("cares00000@gmail.com")
            }
        }

        scm {
            connection.set("scm:git:git://github.com/cares0/rest-docs-kdsl.git")
            developerConnection.set("scm:git:ssh://github.com/cares0/rest-docs-kdsl.git")
            url.set("https://github.com/cares0/rest-docs-kdsl.git")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}

dependencies {
    implementation(kotlin("stdlib"))
    api("org.springframework.restdocs:spring-restdocs-core:3.0.1")
    api("org.springframework.restdocs:spring-restdocs-mockmvc:3.0.1")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}