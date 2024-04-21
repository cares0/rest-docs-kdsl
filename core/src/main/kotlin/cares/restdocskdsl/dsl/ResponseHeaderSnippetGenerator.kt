package cares.restdocskdsl.dsl

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.snippet.Snippet

interface ResponseHeaderSnippetGenerator<C: ApiComponent<HeaderDescriptor>> : SnippetGenerator {

    fun responseHeader(dsl: C.() -> Unit) {
        val responseHeaderComponent = getRequestHeaderApiComponent()
        responseHeaderComponent.dsl()
        addSnippet(
            generateRequestHeaderSnippet(
                element = responseHeaderComponent,
            )
        )
    }

    fun generateRequestHeaderSnippet(
        element: ApiComponent<HeaderDescriptor>,
    ): Snippet {
        return HeaderDocumentation.responseHeaders(
            element.apiValues.map { it.descriptor }
        )
    }

    fun getRequestHeaderApiComponent(): C

}