# REST Docs KDSL

## Overview
This library provides a Kotlin DSL to simplify and enhance 
the readability of code used for documenting APIs with Spring REST Docs.

Using [KSP](https://kotlinlang.org/docs/ksp-overview.html), the library analyzes Spring handlers at compile time 
and generates DSL classes for each handler. 
These generated DSL classes allow you to write Spring REST Docs test code 
and generate adoc files for each API.

## Documentation
#### [cares0.github.io/rest-docs-kdsl](https://cares0.github.io/rest-docs-kdsl)

## Compatibility Matrix

| Library Version | Kotlin Version | KSP Version        | Spring REST Docs Version |
|-----------------|----------------|--------------------|--------------------------|
| `1.0.4`         | `2.0.0`        | `2.0.0-1.0.21`     | `3.0.1`                   |

Version 1.0.4 is the minimum version required for this library.

## Setup

### build.gradle

#### 1. Add the KSP Plugin
```kotlin
plugins {
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
}
```

#### 2. Add Library Dependencies
```kotlin
val kdslVersion = "1.x.x" // Specify the version of the library you want to use.

dependencies {
    implementation("io.github.cares0:rest-docs-kdsl-ksp:$kdslVersion")
    ksp("io.github.cares0:rest-docs-kdsl-ksp:$kdslVersion")
}
```

---
## LICENSE
```
Copyright 2024 YoungJun Kim

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```