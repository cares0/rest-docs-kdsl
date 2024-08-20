package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.snippet.Snippet

interface PathVariableSnippetGenerator<C: ApiComponent<ParameterDescriptor>> : SnippetGenerator {

    fun pathVariables(dsl: C.() -> Unit) {
        val pathVariableComponent = getPathVariableApiComponent()
        pathVariableComponent.dsl()
        addSnippet(
            generatePathVariableSnippet(
                element = pathVariableComponent,
            )
        )
    }

    fun generatePathVariableSnippet(
        element: ApiComponent<ParameterDescriptor>,
    ): Snippet {
        return RequestDocumentation.pathParameters(
            element.apiValues.map { it.descriptor }
        )
    }

    fun getPathVariableApiComponent(): C

}