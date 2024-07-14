package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.BodyElement
import io.github.cares0.restdocskdsl.core.RequestBodyElement

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