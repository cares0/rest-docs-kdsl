package io.github.cares0.restdocskdsl.dsl

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.snippet.Snippet

interface ResponseBodySnippetGenerator<C: FieldComponent> : SnippetGenerator {

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

        for (apiValue in apiComponent.apiValues) {
            if (apiValue is NestedFieldValue<*>) {
                descriptors.add(apiValue.descriptor)
                snippets.addAll(
                    generateResponseBodySnippets(
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