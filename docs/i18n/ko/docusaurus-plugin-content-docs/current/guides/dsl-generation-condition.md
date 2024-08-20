---
sidebar_position: 2
---

# DSL 생성 조건

이 라이브러리는 핸들러를 분석하고, DSL 인터페이스 구현체를 생성하고 있습니다.

현재는 [KSP](https://kotlinlang.org/docs/ksp-overview.html)를 통해 클래스 및 함수의 심볼을 분석하고 있습니다.

이 파트에서는 심볼이 어떤 요소로 결정되어 DSL 구현체를 생성하는지 기술합니다.

## 핸들러 결정 조건

함수가 핸들러로 결정되는 경우에는 `ApiSpec`의 구현체를 생성하게 됩니다.
 
이 라이브러리에서는 Spring Web MVC의 [Mapping Request](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html)에 기반하여 특정 함수를 핸들러로 결정하고 있고, 조건은 아래와 같습니다.

1. 핸들러가 선언된 클래스에 `@RestController` `@Controller` 어노테이션이 달려있어야 합니다.
2. `@RestController` 어노테이션이 달린 클래스의 함수인 경우 Spring의 [요청 매핑 어노테이션](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html#mvc-ann-requestmapping-annotation)이 달려있어야 합니다.
3. `@Controller` 어노테이션이 달린 클래스인 경우 `@ResponseBody` 어노테이션과 요청 매핑 어노테이션이 달려있어야 합니다.

## 컴포넌트 결정 조건

핸들러로 결정된 함수에 대해서 해당 함수의 어노테이션, 파라미터, 리턴타입을 분석하여 어떤 HTTP 컴포넌트인지 결정하게 됩니다.
결정된 각 요소는 `HandlerElement`구현체로 변환되며, 
해당 구현체 타입에 따라 적절한 `ApiComponent`구현체에 `ApiValue`타입의 멤버 프로퍼티로 선언됩니다.

컴파일 시점에 값을 확인할 수 없는 등의 제약되는 사항을 제외하고는 
Spring Web MVC의 [Handler Methods](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods.html)에 기반하여 컴포넌트를 결정하고 있습니다.

### 요청 컴포넌트

#### 경로변수
1. 핸들러 함수 내 `@PathVariable` 어노테이션이 달린 파라미터가 선언되어 있는 경우 해당 파라미터는 경로변수로 분석합니다.
   - 파라미터 타입이 Java, Kotlin API이거나 Enum 이어야 합니다.

#### 쿼리 파라미터
1. 핸들러 함수 내 `@RequestParam` 어노테이션이 달린 파라미터가 선언되어 있는 경우 해당 파라미터는 쿼리 파라미터로 분석합니다.
   - 파라미터 타입이 Java, Kotlin API이거나 Enum이어야 합니다.
   - `@RequestParam` 어노테이션의 name, value 속성에 초기화 된 문자열 값을 쿼리 파라미터의 이름으로 결정합니다.
   - 위 경우에 해당하지 않는 경우, 파라미터의 이름을 쿼리 파라미터의 이름으로 결정합니다.
2. 핸들러 함수 내 `@ModelAttribute` 어노테이션이 달린 파라미터가 선언되어 있는 경우 해당 파라미터 타입에 선언된 모든 프로퍼티를 쿼리 파라미터로 분석합니다.
    - 파라미터 타입이 Java, Kotlin API이거나 Enum이 아니어야 합니다.
    - 각 프로퍼티의 이름을 쿼리 파라미터의 이름으로 결정합니다.
3. 핸들러 함수 내 어노테이션이 달리지 않은 파라미터가 선언되어 있는 경우 해당 파라미터는 쿼리 파라미터로 분석합니다.
   - 파라미터 타입이 Spring, Servlet API가 아니어야 합니다.
   - 파라미터 타입이 Java, Kotlin API이거나 Enum인 경우, 단일 파라미터로 분석하여 해당 파라미터의 이름을 쿼리 파라미터의 이름으로 결정합니다. 
   - 파라미터 타입이 이외의 타입인 경우, 객체 파라미터로 분석하여 파라미터 타입에 선언된 각 프로퍼티의 이름을 쿼리 파라미터의 이름으로 결정합니다.

#### 파트
1. 핸들러 함수 내 `@RequestPart` 어노테이션이 달린 파라미터가 선언되어 있는 경우 해당 파라미터는 파트로 분석합니다.
   - 파라미터 타입이 Java, Kotlin, Spring API이거나 Enum이어야 합니다.
   - `@RequestPart` 어노테이션의 name, value 속성에 초기화 된 문자열 값을 파트의 이름으로 결정합니다.
   - 위 경우에 해당하지 않는 경우, 파라미터의 이름을 파트의 이름으로 결정합니다.

#### 헤더
1. 핸들러 함수 내 `@RequestHeader` 어노테이션이 달린 파라미터가 선언되어 있는 경우 해당 파라미터는 헤더로 분석합니다.
   - 파라미터 타입이 Java, Kotlin API이거나 Enum이어야 합니다. 
   - `@RequestHeader` 어노테이션의 name, value 속성에 초기화 된 문자열 값을 헤더의 이름으로 결정합니다.
   - 위 경우에 해당하지 않는 경우, 파라미터의 이름을 헤더의 이름으로 결정합니다.
2. 핸들러 함수 선언부에 `@RequestHeaderDocs` 어노테이션이 달린 경우, 해당 어노테이션의 name, value 속성에 초기화된 문자열 배열의 모든 값을 헤더로 판단합니다.

#### 쿠키
1. 핸들러 함수 내 `@CookieValue` 어노테이션이 달린 파라미터가 선언되어 있는 경우 해당 파라미터는 쿠키로 분석합니다.
   - 파라미터 타입이 Java, Kotlin API이거나 Enum이어야 합니다.
   - `@CookieValue` 어노테이션의 name, value 속성에 초기화 된 문자열 값을 쿠키의 이름으로 결정합니다.
   - 위 경우에 해당하지 않는 경우, 파라미터의 이름을 쿠키의 이름으로 결정합니다.
2. 핸들러 함수 선언부에 `@RequestCookieDocs` 어노테이션이 달린 경우, 해당 어노테이션의 name, value 속성에 초기화된 문자열 배열의 모든 값을 쿠키로 판단합니다.

#### 바디
1. 핸들러 함수 내 `@RequestBody` 어노테이션이 달린 파라미터가 선언되어 있는 경우 해당 파라미터는 바디로 분석합니다.
   - 파라미터 타입이 단일 파라미터가 아니어야 함니다.
     - 파라미터 타입이 Spring, Servlet API가 아니고, 코틀린 빌트인 타입이 아니어야 합니다.
     - Array, List, Set과 같은 배열 타입으로 JSON이 역직렬화 되는 경우 해당 타입의 타입 파라미터가 위의 조건에 충족되어야 합니다.
   - 파라미터 타입에 선언된 각 프로퍼티의 이름을 필드 이름으로 결정합니다.
     - 프로퍼티 타입이 단일 파라미터가 아닌 경우, 중첩된 타입에 선언된 각 프로퍼티의 이름을 필드 이름으로 결정합니다.

### 응답 컴포넌트

#### 헤더
1. 핸들러 함수 선언부에 `@ResponseHeaderDocs` 어노테이션이 달린 경우, 해당 어노테이션의 name, value 속성에 초기화된 문자열 배열의 모든 값을 헤더로 판단합니다.

#### 쿠키
1. 핸들러 함수 선언부에 `@ResponseCookieDocs` 어노테이션이 달린 경우, 해당 어노테이션의 name, value 속성에 초기화된 문자열 배열의 모든 값을 쿠키로 판단합니다.

#### 바디
1. 핸들러 함수의 리턴 타입이 Spring, Servlet API가 아니고, 코틀린 빌트인 타입이 아닌 경우 해당 리턴 타입을 바디로 분석합니다.
   - Array, List, Set과 같은 배열 타입으로 JSON이 역직렬화 되는 경우 해당 타입의 타입 파라미터가 위의 조건에 충족되어야 합니다.
   - 파라미터 타입에 선언된 각 프로퍼티의 이름을 필드 이름으로 결정합니다.
     - 프로퍼티 타입이 단일 파라미터가 아닌 경우, 중첩된 타입에 선언된 각 프로퍼티의 이름을 필드 이름으로 결정합니다.
2. Spring API 중 `ResponseEntity`는 바디로 분석합니다. 
   - `ResponseEntity`의 타입 파라미터가 Spring, Servlet API가 아니고, 코틀린 빌트인 타입이 아니어야 합니다.
   - 파라미터 타입에 선언된 각 프로퍼티의 이름을 필드 이름으로 결정합니다.
     - 프로퍼티 타입이 단일 파라미터가 아닌 경우, 중첩된 타입에 선언된 각 프로퍼티의 이름을 필드 이름으로 결정합니다.
