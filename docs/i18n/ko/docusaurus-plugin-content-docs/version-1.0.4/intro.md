---
sidebar_position: 1
---

# 소개

이 라이브러리는 Spring REST Docs의 문서 작성 코드를 더 간단하고, 가독성있게 작성할 수 있도록 Kotlin DSL을 제공합니다.

[KSP](https://kotlinlang.org/docs/ksp-overview.html)를 통해 컴파일 시점에 스프링 핸들러를 분석하여 각 핸들러마다 DSL 클래스를 생성합니다. 
생성된 DSL 클래스를 통해 하나의 API에 대한 Spring REST Docs 테스트 코드를 작성하고 adoc 파일을 생성할 수 있습니다.

---

## 기존의 문제점

### 1. 복잡한 코드 구조
요청 시 쿼리 파라미터 1개가 필요하고, 바디에 필드가 2개 있는 JSON을 응답하는 API의 문서를 작성하기 위해
Spring REST Docs를 사용하여 코드를 작성해봅시다.
```kotlin
MockMvcRestDocumentation.document(
    "identifier",
    RequestDocumentation.queryParameters(
        RequestDocumentation.parameterWithName("param1")
            .optional()
            .description("param1"),
    ),
    PayloadDocumentation.responseFields(
        PayloadDocumentation.fieldWithPath("field1")
            .type(JsonFieldType.STRING)
            .description("field1"),
        PayloadDocumentation.fieldWithPath("field2")
            .type(JsonFieldType.NUMBER)
            .attributes(Attributes.key("format").value("number"))
            .description("field2"),
    ),    
)
```
위와 같이 간단한 API임에도 불구하고, 여러 줄의 소스코드를 작성해야 합니다.

만약 요청 및 응답에 사용되는 요소들이 엄청 많은 API에 대해 문서를 작성해야 한다고 생각해봅시다.
소스코드는 수십 수백줄을 넘어가게 되어 유지보수 하기에 굉장히 힘들고, 
처음 코드를 작성하면 Import 해야 하는 패키지도 많기 때문에 문서를 작성하기 굉장히 불편합니다.

### 2. API 스펙 확인의 번거로움
테스트 코드를 먼저 작성하는 경우가 아니라, API를 개발한 후 문서를 작성하는 경우 
해당 API가 어떤 스펙으로 이루어져 있는지 일일이 기억하고 있지 않는 이상 핸들러 코드를 확인해가며 요청 파라미터가 뭔지, 응답 바디에는 어떤 필드들이 있는지
각각의 필요한 요소들의 이름들을 확인해 가면서 문서를 작성해야 합니다.

### 3. 유지보수의 어려움
되도록 API가 변경되는 일은 없어야겠지만, 테스트 코드를 먼저 수정하고 API를 수정하는 경우가 아니라면 API 수정에 따라 문서 작성 코드도 수정해야 합니다. 
이런 경우 직접 해당 API를 테스트 하는 부분을 찾아가 수정하거나, 실제 테스트의 결과의 오류를 보고 찾아가 수정해야 합니다.

---

## 개선사항

### 1. Kotlin DSL로 간소화
DSL은 Domain Specific Language의 약자로, 특정 도메인에 국한된, 특정 도메인을 위해 만들어진 언어를 의미합니다.

Kotlin은 확장함수, 중위 표기 함수, 함수형 프로그래밍, 연산자 오버로딩 등을 지원하기 때문에 이를 활용해 DSL을 만들 수 있습니다.
이 라이브러리는 이러한 Kotlin의 특징을 이용하여 장황한 코드를 Spring REST Docs 도메인에 특화된 DSL로 간소화합니다.

위에서 작성했던 Spring REST Docs 예시 코드를 이 라이브러리에서 만든 Kotlin DSL로 변경해보겠습니다. 
```kotlin
document(ExampleApiSpec("identifier")) {
    queryParameters {
        param1 means "param1" isOptional true
    }
    responseBody {
        field1 means "field1" typeOf STRING
        field2 means "field2" typeOf NUMBER formattedAs "number"
    }
}
```
코드가 이전보다 훨씬 가독성이 좋으며, 필드에 대한 설명이 명확해졌습니다.

### 2.  API 스펙 확인의 용이성
모든 DSL은 Kotlin의 함수형 프로그래밍을 기반으로 작성할 수 있으며, 각 함수는 특정 DSL 클래스를 수신객체로 가지는 함수입니다.

위의 1번 예시를 다시 살펴보면, `queryParameters`, `responseBody` 함수는 DSL을 제공하는 `ExampleApiSpec`의 멤버 함수이며, 
`document` 함수의 2번째 파라미터로 들어가는 함수는 `ExampleApiSpec`을 수신객체로 가지는 함수입니다.

따라서 IDE의 코드 컴플리션 기능을 활용한다면 `this` 키워드를 통해 해당 API가 어떤 스펙으로 이루어져 있는지 손쉽게 확인할 수 있습니다.
```kotlin
document(ExampleApiSpec("identifier")) {
    this.queryParameters {
        this.param1 means "param1" isOptional true
    }
    this.responseBody {
        this.field1 means "field1" typeOf STRING
        this.field2 means "field2" typeOf NUMBER formattedAs "number"
    }
}
```
`this` 키워드를 작성하면 IDE의 코드 컴플리션에 호출 가능한 `queryParameters`, `responseBody` 함수가 보여집니다.
각 함수 내부의 `param1`, `field1`과 같은 프로퍼티 역시 `this` 키워드를 통해 멤버 프로퍼티를 모두 확인할 수 있습니다.

### 3. 컴파일 시점에 변경점 파악
이 라이브러리는 KSP를 통해 컴파일 시점에 DSL 관련 구현체들을 생성합니다. 
따라서 코드 수정 시 테스트를 실행시키지 않고 컴파일 오류를 통해 변경점을 파악할 수 있습니다.