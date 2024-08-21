package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.snippet.AbstractDescriptor

/**
 * A class corresponding to a [org.springframework.restdocs.snippet.Snippet].
 *
 * This class is used by the [SnippetGenerator] to convert each type into the appropriate snippet.
 *
 * @see ApiField
 * @see SnippetGenerator
 * @author YoungJun Kim
 */
abstract class ApiComponent<T: AbstractDescriptor<T>> {
    internal val apiFields: MutableList<ApiField<T>> = mutableListOf()

    fun addFields(vararg fields: ApiField<T>) {
        this.apiFields.addAll(fields)
    }
}