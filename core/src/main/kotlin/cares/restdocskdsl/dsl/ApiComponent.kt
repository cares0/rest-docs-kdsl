package cares.restdocskdsl.dsl

import org.springframework.restdocs.snippet.AbstractDescriptor

abstract class ApiComponent<T: AbstractDescriptor<T>> {
    internal val apiValues: MutableList<ApiValue<T>> = mutableListOf()

    fun addValues(vararg values: ApiValue<T>) {
        this.apiValues.addAll(values)
    }
}