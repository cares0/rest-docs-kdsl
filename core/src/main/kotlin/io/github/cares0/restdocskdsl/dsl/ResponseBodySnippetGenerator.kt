package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.snippet.Snippet

interface ResponseBodySnippetGenerator<C: BodyComponent> : SnippetGenerator {

    fun responseBody(dsl: C.(element: C) -> Unit) {
        val responseBodyComponent = getResponseBodyApiComponent()
        responseBodyComponent.dsl(responseBodyComponent)
        addSnippets(
            generateResponseBodySnippets(
                apiComponent = responseBodyComponent,
                name = "",
                previousBeneathPathName = if (responseBodyComponent.isParentStartWithArray) "[]" else null,
            )
        )
    }

    fun generateResponseBodySnippets(
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
                    generateResponseBodySnippets(
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
            snippets.add(PayloadDocumentation.responseFields(descriptors))
        } else {
            snippets.add(
                PayloadDocumentation.responseFields(
                    PayloadDocumentation.beneathPath(previousBeneathPathName).withSubsectionId(name),
                    descriptors
                )
            )
        }

        return snippets
    }

    fun getResponseBodyApiComponent(): C

}