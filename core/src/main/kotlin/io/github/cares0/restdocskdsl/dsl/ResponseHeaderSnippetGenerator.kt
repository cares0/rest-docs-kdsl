package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.snippet.Snippet

interface ResponseHeaderSnippetGenerator<C: ApiComponent<HeaderDescriptor>> : SnippetGenerator {

    fun responseHeaders(dsl: C.() -> Unit) {
        val responseHeaderComponent = getResponseHeaderApiComponent()
        responseHeaderComponent.dsl()
        addSnippet(
            generateResponseHeaderSnippet(
                element = responseHeaderComponent,
            )
        )
    }

    fun generateResponseHeaderSnippet(
        element: ApiComponent<HeaderDescriptor>,
    ): Snippet {
        return HeaderDocumentation.responseHeaders(
            element.apiFields.map { it.descriptor }
        )
    }

    fun getResponseHeaderApiComponent(): C

}