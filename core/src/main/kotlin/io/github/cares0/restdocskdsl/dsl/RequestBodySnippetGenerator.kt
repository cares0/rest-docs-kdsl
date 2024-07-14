package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.snippet.Snippet

interface RequestBodySnippetGenerator<C: FieldComponent> : SnippetGenerator {

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

        for (apiValue in apiComponent.apiValues) {
            if (apiValue is NestedFieldValue<*>) {
                descriptors.add(apiValue.descriptor)
                snippets.addAll(
                    generateRequestBodySnippets(
                        apiComponent = apiValue.nestedElement,
                        name = apiValue.name,
                        previousBeneathPathName = if (previousBeneathPathName != null)
                            "$previousBeneathPathName.${apiValue.beneathPathName}"
                        else apiValue.beneathPathName
                    )
                )
            } else if (apiValue is FieldValue) {
                descriptors.add(apiValue.descriptor)
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