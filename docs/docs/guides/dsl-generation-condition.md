---
sidebar_position: 2
---

# DSL Generation Conditions

This library analyzes handlers and generates DSL interface implementations based on the analysis.

Currently, class and function symbols are analyzed using [KSP (Kotlin Symbol Processing)](https://kotlinlang.org/docs/ksp-overview.html).

This section explains how symbols are determined and how DSL implementations are generated based on them.

## Handler Determination Conditions

When a function is identified as a handler, an implementation of `ApiSpec` is generated.

This library determines whether a specific function is a handler 
based on the [Mapping Requests](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html) in Spring Web MVC. 
The conditions are as follows:

1. The class where the handler is declared must have the `@RestController` or `@Controller` annotation.
2. If the class is annotated with `@RestController`, the function must have a [request mapping annotation](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html#mvc-ann-requestmapping-annotation) from Spring.
3. If the class is annotated with `@Controller`, the function must have both the `@ResponseBody` annotation and a request mapping annotation.

## Component Determination Conditions

For functions identified as handlers, the function’s annotations, parameters, 
and return types are analyzed to determine the appropriate HTTP components. 
Each determined element is converted into a `HandlerElement` implementation. 
Depending on the type of implementation, 
it is declared as a member property of the appropriate `ApiComponent` implementation with an `ApiValue` type.

Except for cases where values cannot be determined at compile time, 
components are determined based on [Handler Methods](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods.html) in Spring Web MVC.

### Request Components

#### Path Variables
1. If a parameter in the handler function is annotated with `@PathVariable`, it is identified as a path variable.
   - The parameter type must be a Java or Kotlin API type or an Enum.

#### Query Parameters
1. If a parameter in the handler function is annotated with `@RequestParam`, it is identified as a query parameter.
   - The parameter type must be a Java or Kotlin API type or an Enum.
   - The name of the query parameter is determined by the initialized value of the `name` or `value` attribute of the `@RequestParam` annotation.
   - If this is not applicable, the name of the parameter is used as the query parameter name.
2. If a parameter in the handler function is annotated with `@ModelAttribute`, all properties declared in that parameter type are identified as query parameters.
   - The parameter type must not be a Java or Kotlin API type or an Enum.
   - The name of each property is used as the query parameter name.
3. If a parameter in the handler function is not annotated, it is identified as a query parameter.
   - The parameter type must not be a Spring or Servlet API type.
   - If the parameter type is a Java or Kotlin API type or an Enum, it is analyzed as a single parameter, and the parameter name is used as the query parameter name.
   - If the parameter type is another type, it is analyzed as an object parameter, and the name of each property declared in that parameter type is used as the query parameter name.

#### Parts
1. If a parameter in the handler function is annotated with `@RequestPart`, it is identified as a part.
   - The parameter type must be a Java, Kotlin, Spring API type, or an Enum.
   - The name of the part is determined by the initialized value of the `name` or `value` attribute of the `@RequestPart` annotation.
   - If this is not applicable, the name of the parameter is used as the part name.

#### Headers
1. If a parameter in the handler function is annotated with `@RequestHeader`, it is identified as a header.
   - The parameter type must be a Java or Kotlin API type or an Enum.
   - The name of the header is determined by the initialized value of the `name` or `value` attribute of the `@RequestHeader` annotation.
   - If this is not applicable, the name of the parameter is used as the header name.
2. If the handler function is annotated with `@RequestHeaderDocs`, all the strings in the `name` or `value` attribute of the annotation are identified as headers.

#### Cookies
1. If a parameter in the handler function is annotated with `@CookieValue`, it is identified as a cookie.
   - The parameter type must be a Java or Kotlin API type or an Enum.
   - The name of the cookie is determined by the initialized value of the `name` or `value` attribute of the `@CookieValue` annotation.
   - If this is not applicable, the name of the parameter is used as the cookie name.
2. If the handler function is annotated with `@RequestCookieDocs`, all the strings in the `name` or `value` attribute of the annotation are identified as cookies.

#### Body
1. If a parameter in the handler function is annotated with `@RequestBody`, it is identified as the request body.
   - The parameter type must not be a single primitive parameter.
      - The parameter type must not be a Spring, Servlet API, or Kotlin built-in type.
      - If the type is a collection type such as `Array`, `List`, or `Set`, where JSON is deserialized, the type parameter must satisfy the above conditions.
   - The name of each property declared in the parameter type is used as the field name.
      - If the property type is not a single primitive, the name of each property declared in the nested type is used as the field name.

### Response Components

#### Headers
1. If the handler function is annotated with `@ResponseHeaderDocs`, all the strings in the `name` or `value` attribute of the annotation are identified as headers.

#### Cookies
1. If the handler function is annotated with `@ResponseCookieDocs`, all the strings in the `name` or `value` attribute of the annotation are identified as cookies.

#### Body
1. If the return type of the handler function is not a Spring or Servlet API type, and it is not a Kotlin built-in type, the return type is analyzed as the response body.
   - If the type is a collection type such as `Array`, `List`, or `Set`, where JSON is deserialized, the type parameter must satisfy the above conditions.
   - The name of each property declared in the return type is used as the field name.
      - If the property type is not a single primitive, the name of each property declared in the nested type is used as the field name.
2. The `ResponseEntity` type in the Spring API is also analyzed as a body.
   - The type parameter of `ResponseEntity` must not be a Spring, Servlet API, or Kotlin built-in type.
   - The name of each property declared in the type parameter is used as the field name.
      - If the property type is not a single primitive, the name of each property declared in the nested type is used as the field name.