---
sidebar_position: 2
---

# Setup

Before using this library, you need to perform some initial setup.

## build.gradle

### 1. Add the KSP Plugin
```kotlin
plugins {
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
}
```

### 2. Add Library Dependencies
```kotlin
val kdslVersion = "1.x.x" // Specify the version of the library you want to use.

dependencies {
    implementation("io.github.cares0:rest-docs-kdsl-ksp:$kdslVersion")
    ksp("io.github.cares0:rest-docs-kdsl-ksp:$kdslVersion")
}
```

## Compatibility Matrix

| Library Version | Kotlin Version | KSP Version        | Spring REST Docs Version |
|-----------------|----------------|--------------------|--------------------------|
| `1.0.3`         | `2.0.0`        | `2.0.0-1.0.21`     | `3.0.1`                   |