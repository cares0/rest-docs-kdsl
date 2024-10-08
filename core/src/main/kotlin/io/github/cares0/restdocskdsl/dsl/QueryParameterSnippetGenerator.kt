package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.snippet.Snippet

interface QueryParameterSnippetGenerator<C: ApiComponent<ParameterDescriptor>> : SnippetGenerator {

    fun queryParameters(dsl: C.() -> Unit) {
        val queryParameterComponent = getQueryParameterApiComponent()
        queryParameterComponent.dsl()
        addSnippet(generateQueryParameterSnippet(queryParameterComponent))
    }

    fun generateQueryParameterSnippet(
        component: ApiComponent<ParameterDescriptor>,
    ): Snippet {
        return RequestDocumentation.queryParameters(
            component.apiFields.map { it.descriptor }
        )
    }

    fun getQueryParameterApiComponent(): C

}