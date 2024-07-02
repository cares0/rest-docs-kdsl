package cares.restdocskdsl.ksp.resolver

import cares.restdocskdsl.core.QueryParameterElement
import cares.restdocskdsl.ksp.getAsIfKsClassDeclaration
import cares.restdocskdsl.ksp.isEnumType
import cares.restdocskdsl.ksp.isJavaApi
import cares.restdocskdsl.ksp.isKotlinApi
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