package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.core.BodyElement
import cares.restdocskdsl.core.ResponseBodyElement

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