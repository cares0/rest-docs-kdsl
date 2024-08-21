package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.snippet.Snippet

interface RequestHeaderSnippetGenerator<C: ApiComponent<HeaderDescriptor>> : SnippetGenerator {

    fun requestHeaders(dsl: C.() -> Unit) {
        val requestHeaderComponent = getRequestHeaderApiComponent()
        requestHeaderComponent.dsl()
        addSnippet(
            generateRequestHeaderSnippet(
                element = requestHeaderComponent,
            )
        )
    }

    fun generateRequestHeaderSnippet(
        element: ApiComponent<HeaderDescriptor>,
    ): Snippet {
        return HeaderDocumentation.requestHeaders(
            element.apiFields.map { it.descriptor }
        )
    }

    fun getRequestHeaderApiComponent(): C

}