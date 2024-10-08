---
sidebar_position: 2
---

# Tutorial

Learn how to create a handler using Spring MVC and generate documentation using a Kotlin DSL class for that handler.

## 1. Create a Handler

Let's start by creating a Spring handler that maps a request.

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

## 2. Compile the Source Code

Compile the source code by running the following command:

```
./gradlew compileKotlin
```

## 3. Check DSL File Generation

Check that the file `TutorialApiSpec.kt` has been generated 
under the `/build/generated/ksp/main/kotlin/${package_where_handler_is_declared}/dsl` package.

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

This file contains the classes that enable DSL-based documentation for the handler.
(If you’ve used Querydsl before, you can think of it as similar to the Q classes.)

You can start documenting by using the class that implements `ApiSpec` (in this example, `TutorialApiSpec`). 
The naming convention for this class is the handler function name + "ApiSpec".

## 4. Write a Test Case

Create an instance of the `ApiSpec` implementation 
and pass it as an argument to the `io.github.cares0.restdocskdsl.dsl.document` function.

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

The constructor parameter should be the identifier for the documentation. 
The second parameter is a function with the `ApiSpec` implementation as the receiver.

## 5. Write the HTTP Components DSL

In the function block of the `document` function's second parameter, 
you can call functions corresponding to the HTTP components 
(e.g., request parameters, response body) defined in the API spec to write the DSL.

Since the API we created earlier includes request parameters and a response body, 
you can call `queryParameters` and `responseBody`.

```kotlin
document(TutorialApiSpec("tutorial")) {
    queryParameters {
    }
    responseBody {
    }
}
```

For more details on the callable functions for each component,  
refer to the [HTTP Components DSL](../guides/dsl-interface.md#http-component-dsl).

## 6. Write the Field DSL

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

The request parameters for the API we created include "id", and the response body contains "id" and "num" fields. 
These fields are declared as properties of the receiver object in the function blocks.
(Refer to the `TutorialApiQueryParameter` and `TutorialApiResponseBody` classes in the `TutorialApiSpec.kt` file.)

Therefore, you can call these properties and write the documentation using infix notations.

For more details on callable infix notations,
refer to [Component DSL](../guides/dsl-interface#component-dsl).

## 7. Run the Test

After completing all the steps, run the test.

Check the generated snippets under `build/generated-snippets/${DOCUMENT_IDENTIFIER}`.

This library does not support creating and integrating Asciidoc documents from the generated snippets.
Therefore, follow the same process as you would with the existing Spring REST Docs. 
For more details, refer to [Working with Asciidoctor](https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/#working-with-asciidoctor).

---

## Next Steps

Now that you've learned the basics of using this library through the tutorial,
it is recommended to explore how this library provides DSLs through the [DSL Interface](../guides/dsl-interface) documentation.

If you prefer writing test code first, 
you can also manually implement the DSL interface to design 
and document the entire request and response spec for your API.

For more detailed usage examples, refer to the `example` package in the GitHub repository, 
which demonstrates the full capabilities of this library.