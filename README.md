# REST Docs KDSL

## Compatibility Matrix

| Library Version | Minimum Kotlin Version | Minumum KSP Version | Minimum Spring Boot Version |
|-----------------| --- | --- | --- |
| 1.0.2           | 2.0.0 | 2.0.0-1.0.21 | 3.0.0 

---
## Overview

This library provides a Kotlin DSL to make writing documentation code for Spring REST Docs simpler and more readable.

Using KSP, it analyzes Spring handlers and generates DSL classes for each handler at compile time. With the generated DSL classes, you can write Spring REST Docs test code for an API and generate the corresponding asciidoc file.

Let's look at a simple handler example.

```kotlin
@PostMapping("/simple-usage")
fun simpleUsage(
    @RequestHeader("X-Csrf-Token") csrfToken: String,
    @RequestBody request: SimpleRequest,
): SimpleResponse {
    return SimpleResponse.FIXTURE
}

data class SimpleRequest(
    val id: String,
    val password: Int,
) {
    companion object {
        val FIXTURE = SimpleRequest(
            id = "id",
            password = 1
        )
    }
}

data class SimpleResponse(
    val result: String,
    val status: UserStatus,
    val detail: SimpleDetail,
) {

    enum class UserStatus {
        ACTIVATED,
        DEACTIVATED,
    }

    data class SimpleDetail(
        val userId: String,
        val password: Int?,
        val profileMessage: String?,
    ) {
        companion object {
            val FIXTURE = SimpleDetail(
                userId = "userId",
                password = 1,
                profileMessage = null,
            )
        }
    }
    companion object {
        val FIXTURE = SimpleResponse(
            result = "Success",
            status = UserStatus.ACTIVATED,
            detail = SimpleDetail.FIXTURE,
        )
    }
}
```

When documenting an API like the one above using Spring REST Docs, the code would typically look like this:

```kotlin
HeaderDocumentation.requestHeaders(
    HeaderDocumentation.headerWithName("X-Csrf-Token")
        .description("CSRF Token")
),
PayloadDocumentation.requestFields(
    PayloadDocumentation.fieldWithPath("id")
        .type(JsonFieldType.STRING)
        .description("User's id"),
    PayloadDocumentation.fieldWithPath("password")
        .type(JsonFieldType.NUMBER)
        .description("User's password"),
),
PayloadDocumentation.responseFields(
    PayloadDocumentation.fieldWithPath("result")
        .type(JsonFieldType.STRING)
        .description("Whether the login was successful"),
    PayloadDocumentation.fieldWithPath("status")
        .type(JsonFieldType.STRING)
        .description("User's status"),
    PayloadDocumentation.subsectionWithPath("detail")
        .type(JsonFieldType.OBJECT)
        .description("User's detail information"),
),
PayloadDocumentation.responseFields(
    PayloadDocumentation.beneathPath("detail").withSubsectionId("detail"),
    PayloadDocumentation.fieldWithPath("userId")
        .type(JsonFieldType.STRING)
        .description("User's id"),
    PayloadDocumentation.fieldWithPath("password")
        .type(JsonFieldType.STRING)
        .ignored()
        .description("User's password"),
    PayloadDocumentation.fieldWithPath("profileMessage")
        .type(JsonFieldType.STRING)
        .optional()
        .description("User's profile message"),
}
```

However, using this library, the same documentation can be written more concisely and readably with Kotlin DSL as shown below:

```kotlin
requestHeaders {
    `X-Csrf-Token` means "CSRF Token" typeOf STRING
}
requestBody {
    id means "User's id" typeOf STRING
    password means "User's password" typeOf NUMBER
}
responseBody {
    result means "Whether the login was successful" typeOf STRING formattedAs "Success or Failure"
    status means "User's status" typeOf ENUM(ExampleController.SimpleResponse.UserStatus::class)
    detail means "User's detail information" typeOf OBJECT of {
        userId means "User's id" typeOf STRING
        password means "User's password" typeOf STRING isIgnored true
        profileMessage means "User's profile message" typeOf STRING isOptional true
    }
}
```

---

## Getting Started

### Setup

1. Add the KSP plugin:

```kotlin
plugins {
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
}
```

1. Add the library dependency

```kotlin
val kdslVersion = "1.x.x" // Please specify the library version to use.

dependencies {
    implementation("io.github.cares0:rest-docs-kdsl-ksp:$kdslVersion")
    ksp("io.github.cares0:rest-docs-kdsl-ksp:$kdslVersion")
}
```

<br>

### Tutorial

1. Create a Spring handler that maps the request:

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

1. Run the `./gradlew compileKotlin` command.
2. Verify that the `TutorialApiSpec.kt` file is generated under the `/build/generated/ksp/main/kotlin/${package_of_the_handler}/dsl` package.

**TutorialApiSpec.kt**
```kotlin
public object TutorialApiResponseBody : FieldComponent(false) {
  public val id: FieldValue = FieldValue("id", false, 0)

  public val num: FieldValue = FieldValue("num", false, 0)

  init {
    addValues(
      id,
      num
    )
  }
}

public object TutorialApiQueryParameter : ApiComponent<ParameterDescriptor>() {
  public val id: QueryParameterValue = QueryParameterValue("id")

  init {
    addValues(
      id
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

This file aggregates the classes that enable writing DSL for the corresponding handler. (If you've used Querydsl before, you can think of this as similar to the Q classes.)

You can start writing the documentation by using the class that implements `ApiSpec` (in the example above, `TutorialApiSpec`). The naming convention for this class is the handler function name + "ApiSpec".

3. Write the test code.

Create an instance of the `ApiSpec` implementation and pass it as an argument to the `io.github.cares0.restdocskdsl.dsl.document` function.

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

The parameter for the constructor is the identifier for the document. The second parameter is a function that takes the implementation as its receiver.

4. Write DSL functions.

In the function block of the second parameter of the `document` function, you can call the available functions according to the HTTP components (request and response cookies, headers, bodies, etc.) defined in the API spec. The table below shows the functions that can be called for each HTTP component.

| Request Path Variables | pathVariables |
| --- | --- |
| Request Parameters | `queryParameters` |
| Request Headers | `requestHeaders` |
| Request Cookies | `requestCookies` |
| Request Parts | `requestParts` |
| Request Body | `requestBody` |
| Response Headers | `responseHeaders` |
| Response Cookies | `responseCookies` |
| Response Body | `responseBody` |

The API we created includes request parameters and a response body, so we can call `queryParameters` and `responseBody`.

```kotlin
document(TutorialApiSpec("tutorial")) {
    queryParameters {
    }
    responseBody {
    }
}

```

5. Document each element.

```kotlin
document(TutorialApiSpec("tutorial")) {
    queryParameters {
        id means "Id" typeOf STRING
    }
    responseBody {
        id means "Id" typeOf STRING
        num means "Number" typeOf NUMBER
    }
}

```

The API we created has a request parameter "id" and a response body with "id and "num" fields. These fields are declared as properties of the function block's receiver. (Refer to the `TutorialApiQueryParameter` and `TutorialApiResponseBody` classes in `TutorialApiSpec.kt` above.) Therefore, you can document them by calling the properties and chaining the infix functions.

Refer to the `io.github.cares0.restdocskdsl.dsl.ApiValue` interface documentation for available infix functions.

For more detailed usage, check out the example code in the `example` package of this GitHub repository.

---
## Advantages

1. No need to remember the API specification.

When writing Spring REST Docs code, it's common to go back and forth between the handler and the test code to verify the API's specifications, such as parameter names and response body fields.

However, with this library, you only need to call the functions available in the `ApiSpec` implementation, eliminating the need to check the API specs. Additionally, you don't need to verify the composition of the body, parameters, etc. This can be even more efficient when using IDE code completion.

Since the function blocks that write the DSL are functions that take their respective implementations as receivers, you can check the member functions and properties using the IDE's code completion through the `this` keyword.

```kotlin
document(TutorialApiSpec("tutorial")) {
    this.queryParameters {
        this.id means "Id" typeOf STRING
    }
    this.responseBody {
        this.id means "Id" typeOf STRING
        this.num means "Number" typeOf NUMBER
    }
}

```

2. Identify API modifications at compile time.

Ideally, API changes should be minimized, but if changes do occur, it used to be necessary to manually locate and update the test code.

However, because this library generates DSL classes at compile time, if the code changes, the receiver's member function or property names will change, allowing you to identify modifications at compile time. This makes it easier to identify and address changes.

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