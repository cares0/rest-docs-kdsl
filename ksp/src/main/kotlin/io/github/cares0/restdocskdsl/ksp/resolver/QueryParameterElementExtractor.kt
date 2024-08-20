package io.github.cares0.restdocskdsl.ksp.resolver

import io.github.cares0.restdocskdsl.core.QueryParameterElement
import io.github.cares0.restdocskdsl.ksp.getAsIfKsClassDeclaration
import io.github.cares0.restdocskdsl.ksp.isEnumType
import io.github.cares0.restdocskdsl.ksp.isJavaApi
import io.github.cares0.restdocskdsl.ksp.isKotlinApi
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSTypeReference

interface QueryParameterElementExtractor {

    val logger: KSPLogger

    fun extractElementWhenCustomObject(
        elementTypeReference: KSTypeReference,
    ): List<QueryParameterElement> {
        val declaredProperties = elementTypeReference.getAsIfKsClassDeclaration()?.getDeclaredProperties() ?: emptySequence()
        return declaredProperties.flatMap { property ->
            extractElement(
                elementTypeReference = property.type,
                elementName = property.simpleName.asString(),
            )
        }.toList()
    }

    fun extractElement(
        elementTypeReference: KSTypeReference,
        elementName: String,
    ): List<QueryParameterElement> {
        return if (elementTypeReference.isJavaApi()
            || elementTypeReference.isKotlinApi()
            || elementTypeReference.isEnumType()
        ) {
            listOf(QueryParameterElement(elementName))
        } else extractElementWhenCustomObject(elementTypeReference)
    }

}