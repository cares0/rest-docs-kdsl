package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.snippet.AbstractDescriptor

/**
 * A class corresponding to a [org.springframework.restdocs.snippet.Snippet].
 *
 * This class is used by the [SnippetGenerator] to convert each type into the appropriate snippet.
 *
 * @see ApiValue
 * @see SnippetGenerator
 * @author YoungJun Kim
 */
abstract class ApiComponent<T: AbstractDescriptor<T>> {
    internal val apiValues: MutableList<ApiValue<T>> = mutableListOf()

    fun addValues(vararg values: ApiValue<T>) {
        this.apiValues.addAll(values)
    }
}