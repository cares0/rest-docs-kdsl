package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.snippet.AbstractDescriptor

/**
 * A class that facilitates the creation of an [AbstractDescriptor] using a DSL.
 *
 * ### Example Usage
 *
 * Using Spring REST Docs:
 *
 * ```kotlin
 * fieldWithPath("fieldName")
 *     .optional()
 *     .type(JsonFieldType.STRING)
 *     .description("description")
 * ```
 *
 * Using the DSL provided by this class:
 *
 * ```kotlin
 * fieldName means "description" typeOf STRING isOptional true
 * ```
 *
 * In the DSL example, `fieldName` is an instance of the `ApiField` class, which is a member of [ApiComponent].
 * This instance will later be converted into an [AbstractDescriptor] through the use of a [SnippetGenerator].
 *
 * @param T The type of the [AbstractDescriptor] that will be generated. For instance, when documenting request parameters, this type corresponds to a [org.springframework.restdocs.request.ParameterDescriptor].
 * @see ApiComponent
 * @see SnippetGenerator
 * @author YoungJun Kim
 */
interface ApiField<T: AbstractDescriptor<T>> {
    val name: String
    val descriptor: T

    /**
     * Corresponds to [AbstractDescriptor.description].
     */
    infix fun means(description: String): ApiField<T>

    /**
     * Corresponds to [org.springframework.restdocs.payload.FieldDescriptor.type].
     * If [T] is not of type `FieldDescriptor`, the type is provided as an argument to the [format] function.
     */
    infix fun typeOf(type: ApiFieldType): ApiField<T>

    /**
     * Adds the given argument to [AbstractDescriptor.attributes] with "format" as the key.
     * This can be utilized later when using [custom snippets](https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/#documenting-your-api-customizing).
     */
    infix fun formattedAs(format: String): ApiField<T>

    /**
     * Corresponds to [org.springframework.restdocs.snippet.IgnorableDescriptor.ignored].
     */
    infix fun isIgnored(isIgnored: Boolean): ApiField<T>

    /**
     * Corresponds to `optional()`.
     */
    infix fun isOptional(isOptional: Boolean): ApiField<T>
}