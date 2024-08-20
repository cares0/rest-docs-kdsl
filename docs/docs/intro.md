---
sidebar_position: 1
---

# Introduction

This library provides a Kotlin DSL to simplify and enhance the readability of code 
used for documenting APIs with Spring REST Docs.

By leveraging [KSP](https://kotlinlang.org/docs/ksp-overview.html), 
the library analyzes Spring handlers at compile time and generates DSL classes for each handler. 
These generated DSL classes allow you to write Spring REST Docs test code and generate adoc files for each API 
in a streamlined manner.

---

## Existing Issues

### 1. Complex Code Structure
Let's consider documenting an API that requires one query parameter in the request 
and responds with a JSON body containing two fields. Using Spring REST Docs, the code might look like this:
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
Even for a simple API like this, you need to write multiple lines of code.

Now imagine documenting an API with a large number of request and response elements.
The source code could easily extend to dozens or even hundreds of lines, making it difficult to maintain. 
Additionally, writing the documentation is cumbersome due to the numerous packages that need to be imported.

### 2. Inconvenience of Checking API Specifications
When documenting an API after it has been developed, as opposed to writing test code first, 
you need to check the handler code to recall the request parameters and response fields. 
Without remembering the specifics of the API, you must manually verify each element's name while writing the documentation.

### 3. Maintenance Challenges
Ideally, APIs should not change frequently. 
However, if the API changes and the test code isn’t updated first, 
you’ll need to update the documentation code accordingly. 
This requires locating and modifying the relevant sections of the test code, 
or identifying and fixing errors based on test results.

---

## Improvements
### 1. Simplification with Kotlin DSL
DSL stands for Domain Specific Language, which refers to a language tailored to a specific domain.

Kotlin supports features like extension functions, infix notation, functional programming, and operator overloading, 
making it ideal for creating DSLs. 
This library leverages these Kotlin features to simplify verbose Spring REST Docs code into a DSL specialized for its domain.

Let’s rewrite the earlier Spring REST Docs example using the Kotlin DSL provided by this library:
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
This code is much more readable and provides clear descriptions for each element.

### 2. Ease of Checking API Specifications
All DSLs can be written based on Kotlin’s functional programming principles, 
with each function having a specific DSL class as its receiver.

In the example above, `queryParameters` and `responseBody` functions are member functions of the DSL class `ExampleApiSpec`, 
and the second parameter of the `document` function is a function with `ExampleApiSpec` as its receiver.

Therefore, using your IDE's code completion feature, 
you can easily verify the specifications of an API using the `this` keyword.
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
When you type the `this` keyword, the IDE shows you the available `queryParameters` and `responseBody` functions. 
Similarly, you can view all member properties like `param1` and `field1` within each function.

### 3. Detecting Changes at Compile Time
This library uses KSP to generate DSL-related implementations at compile time. 
This allows you to identify changes through compile errors rather than having to run tests, 
making it easier to maintain the documentation code.