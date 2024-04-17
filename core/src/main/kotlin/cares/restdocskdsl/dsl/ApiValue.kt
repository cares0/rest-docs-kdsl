package cares.restdocskdsl.dsl

import org.springframework.restdocs.snippet.AbstractDescriptor

interface ApiValue<T: AbstractDescriptor<T>> {
    val name: String
    val descriptor: T

    infix fun means(description: String): ApiValue<T>
    infix fun typeOf(type: ApiValueType): ApiValue<T>
    infix fun formattedAs(format: String): ApiValue<T>
    infix fun isIgnored(isIgnored: Boolean): ApiValue<T>
    infix fun isOptional(isOptional: Boolean): ApiValue<T>
}