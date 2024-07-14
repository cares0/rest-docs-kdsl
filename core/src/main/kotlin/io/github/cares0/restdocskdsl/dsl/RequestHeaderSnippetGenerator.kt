package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.snippet.Snippet

interface RequestHeaderSnippetGenerator<C: ApiComponent<HeaderDescriptor>> : SnippetGenerator {

    fun requestHeader(dsl: C.() -> Unit) {
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
            element.apiValues.map { it.descriptor }
        )
    }

    fun getRequestHeaderApiComponent(): C

}