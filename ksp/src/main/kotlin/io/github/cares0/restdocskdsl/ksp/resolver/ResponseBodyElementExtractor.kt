package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.BodyElement
import io.github.cares0.restdocskdsl.core.ResponseBodyElement

interface ResponseBodyElementExtractor : BodyElementExtractor {

    override fun createBodyElement(
        name: String,
        nestedElementName: String?,
        nestedElements: List<BodyElement>?,
        isArrayBasedType: Boolean,
        isRootElement: Boolean,
    ): BodyElement {
        return ResponseBodyElement(
            name = name,
            nestedElementName = nestedElementName,
            nestedElements = nestedElements,
            isArrayBasedType = isArrayBasedType,
            isRootElement = isRootElement,
        )
    }

}