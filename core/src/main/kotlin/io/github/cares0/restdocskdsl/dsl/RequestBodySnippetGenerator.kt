package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.snippet.Snippet

interface RequestBodySnippetGenerator<C: BodyComponent> : SnippetGenerator {

    fun requestBody(dsl: C.(element: C) -> Unit) {
        val requestBodyComponent = getRequestBodyApiComponent()
        requestBodyComponent.dsl(requestBodyComponent)
        addSnippets(
            generateRequestBodySnippets(
                apiComponent = requestBodyComponent,
                name = "",
                previousBeneathPathName = if (requestBodyComponent.isParentStartWithArray) "[]" else null,
            )
        )
    }

    fun generateRequestBodySnippets(
        apiComponent: ApiComponent<FieldDescriptor>,
        name: String,
        previousBeneathPathName: String? = null,
    ): List<Snippet> {
        val snippets = mutableListOf<Snippet>()
        val descriptors = mutableListOf<FieldDescriptor>()

        for (apiField in apiComponent.apiFields) {
            if (apiField is NestedJsonField<*>) {
                descriptors.add(apiField.descriptor)
                snippets.addAll(
                    generateRequestBodySnippets(
                        apiComponent = apiField.nestedElement,
                        name = apiField.name,
                        previousBeneathPathName = if (previousBeneathPathName != null)
                            "$previousBeneathPathName.${apiField.beneathPathName}"
                        else apiField.beneathPathName
                    )
                )
            } else if (apiField is JsonField) {
                descriptors.add(apiField.descriptor)
            }
        }

        if (previousBeneathPathName.isNullOrBlank()) {
            snippets.add(PayloadDocumentation.requestFields(descriptors))
        } else {
            snippets.add(
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.beneathPath(previousBeneathPathName).withSubsectionId(name),
                    descriptors
                )
            )
        }

        return snippets
    }

    fun getRequestBodyApiComponent(): C

}