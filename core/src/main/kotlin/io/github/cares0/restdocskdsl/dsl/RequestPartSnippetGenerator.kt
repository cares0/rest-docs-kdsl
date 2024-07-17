package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestPartDescriptor
import org.springframework.restdocs.snippet.Snippet

interface RequestPartSnippetGenerator<C: ApiComponent<RequestPartDescriptor>> : SnippetGenerator {

    fun requestParts(dsl: C.() -> Unit) {
        val requestPartComponent = getRequestPartApiComponent()
        requestPartComponent.dsl()
        addSnippet(generateRequestPartSnippet(requestPartComponent))
    }

    fun generateRequestPartSnippet(
        component: ApiComponent<RequestPartDescriptor>,
    ): Snippet {
        return RequestDocumentation.requestParts(
            component.apiValues.map { it.descriptor }
        )
    }

    fun getRequestPartApiComponent(): C

}