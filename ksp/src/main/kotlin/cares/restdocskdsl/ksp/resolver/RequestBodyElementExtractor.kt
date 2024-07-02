package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.core.BodyElement
import cares.restdocskdsl.core.RequestBodyElement

interface RequestBodyElementExtractor : BodyElementExtractor  {

    override fun createBodyElement(
        name: String,
        nestedElementName: String?,
        nestedElements: List<BodyElement>?,
        isArrayBasedType: Boolean,
        isRootElement: Boolean,
    ): BodyElement {
        return RequestBodyElement(
            name = name,
            nestedElementName = nestedElementName,
            nestedElements = nestedElements,
            isArrayBasedType = isArrayBasedType,
            isRootElement = isRootElement,
        )
    }

}