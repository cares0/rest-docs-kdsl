---
sidebar_position: 2
---

# 설정

이 라이브러리를 사용하기 위해 선행되어야 하는 설정을 진행합니다.

## build.gradle

### 1. KSP 플러그인 추가
```kotlin
plugins {
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
}
```

### 2. 라이브러리 의존성 추가
```kotlin
val kdslVersion = "1.x.x" // 사용할 라이브러리 버전을 작성하세요.

dependencies {
    implementation("io.github.cares0:rest-docs-kdsl-ksp:$kdslVersion")
    ksp("io.github.cares0:rest-docs-kdsl-ksp:$kdslVersion")
}
```

## 호환성 매트릭스
| 라이브러리 버전 | Kotlin 버전 | KSP 버전 | Spring REST Docs 버전 |
|----------|-------------|-----------------|--------------------|
| `1.0.4`  | `2.0.0`     | `2.0.0-1.0.21` | `3.0.1` |

1.0.4 버전이 이 라이브러리의 최소 버전입니다.