---
sidebar_position: 2
---

# 튜토리얼

Spring MVC를 사용해 핸들러를 작성하고, 작성한 핸들러에 대해 DSL 클래스를 생성하여 문서를 작성하는 방법을 배웁니다.

## 1. 핸들러 작성

요청을 매핑하는 스프링 핸들러를 하나 만들어봅시다.

```kotlin
@GetMapping("/tutorial")
fun tutorial(
    @RequestParam id: String,
): TutorialResponse {
    return TutorialResponse(id, 1)
}

data class TutorialResponse(
    val id: String,
    val num: Int,
)
```

## 2. 소스코드 컴파일
아래 커맨드를 실행하여 소스코드를 컴파일 합니다.
```
./gradlew compileKotlin
```

## 3. DSL 파일 생성 확인
`/build/generated/ksp/main/kotlin/${핸들러가_선언된_패키지}/dsl` 패키지 아래에 `TutorialApiSpec.kt` 파일이 생성된 것을 확인합니다.

**TutorialApiSpec.kt**
```kotlin
public object TutorialApiResponseBody : BodyComponent(false) {
  public val id: JsonField = JsonField("id", false, 0)

  public val num: JsonField = JsonField("num", false, 0)

  init {
    addFields(
      `id`,
      `num`
    )
  }
}

public object TutorialApiQueryParameter : ApiComponent<ParameterDescriptor>() {
  public val id: QueryParameterField = QueryParameterField("id")

  init {
    addFields(
      `id`
    )
  }
}

public data class TutorialApiSpec(
  override val identifier: String,
) : ApiSpec,
    ResponseBodySnippetGenerator<TutorialApiResponseBody>,
    QueryParameterSnippetGenerator<TutorialApiQueryParameter> {
  override val snippets: MutableList<Snippet> = mutableListOf()

  override fun getResponseBodyApiComponent(): TutorialApiResponseBody = TutorialApiResponseBody

  override fun getQueryParameterApiComponent(): TutorialApiQueryParameter =
      TutorialApiQueryParameter

  override fun addSnippet(generatedSnippet: Snippet) {
    this.snippets.add(generatedSnippet)
  }

  override fun addSnippets(generatedSnippets: List<Snippet>) {
    this.snippets.addAll(generatedSnippets)
  }
}
```

위 파일이 바로 해당 핸들러를 분석하여 DSL을 작성할 수 있도록 하는 클래스를 모아놓은 파일입니다. 
(혹시 Querydsl 을 사용해본 적이 있다면, Q클래스와 비슷한 맥락이라고 생각하면 됩니다.)

`ApiSpec`을 구현하고 있는 클래스(위 예제에서는 `TutorialApiSpec`)를 통해 문서 작성을 시작할 수 있습니다. 
해당 클래스의 명명규칙은 핸들러 함수 이름 + "ApiSpec" 입니다.

## 4. 테스트 코드 작성

`ApiSpec` 구현체의 인스턴스를 생성하여 `io.github.cares0.restdocskdsl.dsl.document` 함수의 인자로 넣어줍니다.

```kotlin
@Test
fun tutorial() {
    mockMvc.post("/tutorial") {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, "/tutorial")
        contentType = MediaType.APPLICATION_JSON
        characterEncoding = StandardCharsets.UTF_8.name()
        content = createJson(ExampleController.SimpleRequest.FIXTURE)
        header("X-Csrf-Token", "token")
    }.andExpectAll {
        status { isOk() }
    }.andDo {
        print()
        document(TutorialApiSpec("tutorial")) {
        
        }
    }
}
```

생성자의 파라미터에는 문서의 구분자(identifier)를 인자로 넣어주시면 됩니다. 두 번째 파라미터는 해당 구현체를 수신객체로 가지는 함수입니다.

## 5. HTTP 컴포넌트 DSL 작성

`document` 함수의 두 번째 파라미터 함수 블록에서 
API 스펙에 정의된 HTTP 컴포넌트들(요청 및 응답 쿠키, 헤더, 바디 등)에 대응되는 함수를 호출하여 DSL을 작성할 수 있습니다.

위에서 만든 API는 요청 파라미터, 응답 바디를 포함하고 있음으로 `queryParameters`, `responseBody` 를 호출할 수 있습니다.

```kotlin
document(TutorialApiSpec("tutorial")) {
    queryParameters {
    }
    responseBody {
    }
}
```
각 컴포넌트 별 호출 가능한 함수에 대해서는 [HTTP 컴포넌트 DSL](../guides/dsl-interface#http-컴포넌트-dsl)을 참고해주세요.

## 6. 필드 DSL 작성

```kotlin
document(TutorialApiSpec("tutorial")) {
    queryParameters {
        id means "Id" typeOf STRING
    }
    responseBody {
        id means "Id" typeOf STRING
        num means "Number" typeOf NUMBER
    }
)
```
위에서 만든 API의 요청 파라미터는 "id", 응답 바디는 "id", "num" 필드로 구성되어 있었습니다. 
해당 필드는 각각 함수 블록의 수신객체의 프로퍼티로 선언이 되어 있습니다. 
(위의`TutorialApiSpec.kt` 에서 `TutorialApiQueryParameter`, `TutorialApiResponseBody` 클래스를 참고해주세요.) 
따라서 해당 프로퍼티를 호출한 후, 중위 함수를 호출해가며 문서를 작성하면 됩니다.

호출 가능한 중위함수에 대해서는 [필드 DSL](../guides/dsl-interface#필드-dsl)을 참고해주세요.

## 7. 테스트 실행
모두 작성한 후 테스트를 실행합니다. 

`build/generated-snippets/${문서식별자}` 하위에 생성된 스니펫을 확인합니다.

생성된 스니펫을 통해 asciidoc 문서를 작성하고 통합하는 과정은 이 라이브러리에서 따로 지원하지 않습니다.
따라서 기존의 Spring REST Docs를 사용하던 것처럼 하면 됩니다.
자세한 내용은 [Working with Asciidoctor](https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/#working-with-asciidoctor)를 참고해주세요.

---
## 다음 단계
튜토리얼을 통해 이 라이브러리의 간략한 사용법을 익혔다면, 
이 라이브러리가 어떤 방식으로 DSL을 제공하는지 [DSL 인터페이스](../guides/dsl-interface) 문서를 통해 알아보시는 걸 추천합니다. 

만약 테스트 코드를 먼저 작성하는 경우에는 DSL 인터페이스를 직접 구현하여 API의 전체 요청 및 응답 스펙을 설계한 후 문서를 작성할 수도 있습니다.

또한 해당 깃허브 리포지토리의 `example` 패키지 아래에 이 라이브러리의 전체 기능을 사용하는 예시 코드가 있으니 더 상세한 사용법은 이를 참고해주세요.